using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

class Session : IDisposable
{
    private readonly Uri url;
    private readonly Socket conn = new Socket(SocketType.Stream, ProtocolType.Tcp);
    private readonly byte[] request;
    private readonly byte[] buffer = new byte[8192];
    private readonly StringBuilder headerLine = new StringBuilder();
    private readonly MemoryStream body = new MemoryStream();
    private readonly TaskCompletionSource<bool> finished = new TaskCompletionSource<bool>();
    private int sent;
    private long? contentLength;
    private bool readingStatus = true;
    private bool headersRead;

    public Session(Uri url)
    {
        this.url = url;
        request = Encoding.ASCII.GetBytes(
            $"GET {url.PathAndQuery} HTTP/1.0\r\nHost: {url.Authority}\r\nConnection: close\r\n\r\n");
    }

    private bool ProcessBuffer(int size)
    {
        int pos = 0;
        while (!headersRead && pos < size)
        {
            char c = (char)buffer[pos++];
            if (c == '\n')
            {
                ProcessHeaderLine(headerLine.ToString().TrimEnd('\r'));
                headerLine.Clear();
            }
            else
            {
                headerLine.Append(c);
            }
        }

        if (size == 0)
        {
            if (!headersRead || (contentLength.HasValue && body.Length != contentLength.Value))
                throw new IOException("Raspuns HTTP incomplet.");
            return true;
        }

        if (headersRead)
        {
            int count = size - pos;
            if (contentLength.HasValue)
                count = (int)Math.Min(count, contentLength.Value - body.Length);
            body.Write(buffer, pos, count);
        }
        return headersRead && body.Length == contentLength;
    }

    private void ProcessHeaderLine(string line)
    {
        if (readingStatus)
        {
            readingStatus = false;
            string[] parts = line.Split(' ', 3);
            if (parts.Length < 2 || !parts[0].StartsWith("HTTP/", StringComparison.Ordinal) ||
                !int.TryParse(parts[1], out int status) || status < 200 || status >= 300)
                throw new IOException($"Raspuns HTTP: {line}");
            return;
        }

        if (line.Length == 0)
        {
            headersRead = true;
            return;
        }

        int colon = line.IndexOf(':');
        if (colon < 0)
            throw new IOException($"Antet HTTP invalid: {line}");

        string name = line.Substring(0, colon);
        if (name.Equals("Content-Length", StringComparison.OrdinalIgnoreCase))
        {
            if (!long.TryParse(line.Substring(colon + 1).Trim(), NumberStyles.None,
                CultureInfo.InvariantCulture, out long length) ||
                (contentLength.HasValue && contentLength.Value != length))
                throw new IOException("Content-Length invalid.");
            contentLength = length;
        }
        else if (name.Equals("Transfer-Encoding", StringComparison.OrdinalIgnoreCase))
        {
            throw new IOException("Transfer-Encoding nu este suportat.");
        }
    }

    // Metoda 1: operatiile si parserul sunt apelate direct din callback-uri.
    public Task RunCallbacks()
    {
        RunCallback(() => conn.BeginConnect(url.IdnHost, url.Port, OnConnected, null));
        return finished.Task;
    }

    private void OnConnected(IAsyncResult ar)
    {
        RunCallback(() =>
        {
            conn.EndConnect(ar);
            BeginSend();
        });
    }

    private void BeginSend()
    {
        conn.BeginSend(request, sent, request.Length - sent, SocketFlags.None, OnSent, null);
    }

    private void OnSent(IAsyncResult ar)
    {
        RunCallback(() =>
        {
            AddSent(conn.EndSend(ar));
            if (sent < request.Length)
                BeginSend();
            else
                BeginReceive();
        }, ar.CompletedSynchronously);
    }

    private void BeginReceive()
    {
        conn.BeginReceive(buffer, 0, buffer.Length, SocketFlags.None, OnReceived, null);
    }

    private void OnReceived(IAsyncResult ar)
    {
        RunCallback(() =>
        {
            if (ProcessBuffer(conn.EndReceive(ar)))
                finished.SetResult(true);
            else
                BeginReceive();
        }, ar.CompletedSynchronously);
    }

    private void RunCallback(Action action, bool completedSynchronously = false)
    {
        if (completedSynchronously)
        {
            ThreadPool.QueueUserWorkItem(_ => RunCallback(action));
            return;
        }

        try
        {
            action();
        }
        catch (Exception ex)
        {
            finished.SetException(ex);
        }
    }

    // Metoda 2: fiecare operatie porneste din continuarea celei precedente.
    public Task RunContinuations()
    {
        return Connect().ContinueWith(task =>
        {
            task.GetAwaiter().GetResult();
            return SendWithContinuations();
        }).Unwrap();
    }

    private Task SendWithContinuations()
    {
        return Send().ContinueWith(task =>
        {
            AddSent(task.GetAwaiter().GetResult());
            return sent < request.Length ? SendWithContinuations() : ReceiveWithContinuations();
        }).Unwrap();
    }

    private Task ReceiveWithContinuations()
    {
        return Receive().ContinueWith(task =>
        {
            return ProcessBuffer(task.GetAwaiter().GetResult())
                ? Task.CompletedTask
                : ReceiveWithContinuations();
        }).Unwrap();
    }

    // Metoda 3: aceleasi operatii pe task-uri, cu async/await.
    public async Task RunAsync()
    {
        await Connect();
        while (sent < request.Length)
            AddSent(await Send());

        while (!ProcessBuffer(await Receive()))
        {
        }
    }

    private void AddSent(int count)
    {
        if (count == 0)
            throw new IOException("Conexiunea s-a inchis in timpul trimiterii.");
        sent += count;
    }

    private Task Connect()
    {
        return SocketTask(
            callback => conn.BeginConnect(url.IdnHost, url.Port, callback, null),
            ar =>
            {
                conn.EndConnect(ar);
                return true;
            });
    }

    private Task<int> Send()
    {
        return SocketTask(
            callback => conn.BeginSend(request, sent, request.Length - sent, SocketFlags.None, callback, null),
            conn.EndSend);
    }

    private Task<int> Receive()
    {
        return SocketTask(
            callback => conn.BeginReceive(buffer, 0, buffer.Length, SocketFlags.None, callback, null),
            conn.EndReceive);
    }

    private static Task<T> SocketTask<T>(Action<AsyncCallback> begin, Func<IAsyncResult, T> end)
    {
        var promise = new TaskCompletionSource<T>(TaskCreationOptions.RunContinuationsAsynchronously);
        try
        {
            begin(ar =>
            {
                try
                {
                    promise.SetResult(end(ar));
                }
                catch (Exception ex)
                {
                    promise.SetException(ex);
                }
            });
        }
        catch (Exception ex)
        {
            promise.SetException(ex);
        }
        return promise.Task;
    }

    public long Save(string fileName)
    {
        using var file = File.Create(fileName);
        body.WriteTo(file);
        return body.Length;
    }

    public void Dispose()
    {
        conn.Dispose();
        body.Dispose();
    }
}

class Program
{
    static int Main(string[] args)
    {
        if (args.Length < 2 || !int.TryParse(args[0], out int method) || method < 1 || method > 3)
        {
            Console.WriteLine("Utilizare: dotnet run -- <1|2|3> <url-http> [url-http ...]");
            return 1;
        }

        Directory.CreateDirectory("downloads");
        var downloads = new List<Task<bool>>();
        for (int i = 1; i < args.Length; i++)
            downloads.Add(Download(method, args[i], i));

        Task<bool[]> all = Task.WhenAll(downloads);
        all.Wait();
        return Array.IndexOf(all.Result, false) < 0 ? 0 : 1;
    }

    private static async Task<bool> Download(int method, string address, int index)
    {
        try
        {
            if (!Uri.TryCreate(address, UriKind.Absolute, out Uri? url) || url.Scheme != Uri.UriSchemeHttp)
                throw new ArgumentException("Foloseste un URL cu http://.");

            using var session = new Session(url);
            switch (method)
            {
                case 1: await session.RunCallbacks(); break;
                case 2: await session.RunContinuations(); break;
                case 3: await session.RunAsync(); break;
            }

            string name = Path.GetFileName(url.AbsolutePath);
            if (name.Length == 0)
                name = "index.html";
            string output = Path.Combine("downloads", $"{index}_{name}");
            long bytes = session.Save(output);
            Console.WriteLine($"{url} -> {output} ({bytes} bytes)");
            return true;
        }
        catch (Exception ex)
        {
            Console.Error.WriteLine($"{address}: {ex.Message}");
            return false;
        }
    }
}
