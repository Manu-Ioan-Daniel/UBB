// A server that accepts pairs of numbers, transmitted as text and separated by whitespace, and sends back their sums

using System;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Threading.Tasks;

struct IntReaderData
{
    public int partValue;
    public bool toContinue;

    public static IntReaderData init()
    {
        IntReaderData ret;
        ret.partValue = 0;
        ret.toContinue = true;
        return ret;
    }
}

static class TaskHelper
{
    private static void auxLoop<R>(Func<R, bool> loopingPredicate, Func<R, Task<R>> loopFunc, R start, TaskCompletionSource<R> ret)
    {
        if (!loopingPredicate(start))
        {
            ret.SetResult(start);
            return;
        }

        Task<R> tmpResFuture = loopFunc(start);
        tmpResFuture.ContinueWith((Task<R> v) => {
            auxLoop(loopingPredicate, loopFunc, v.Result, ret);
        });
    }

    public static Task<R> executeAsyncLoop<R>(Func<R, bool> loopingPredicate, Func<R, Task<R>> loopFunc, R start)
    {
        TaskCompletionSource<R> ret = new TaskCompletionSource<R>();
        auxLoop(loopingPredicate, loopFunc, start, ret);
        return ret.Task;
    }

}

class Session
{
    private Session(Socket conn)
    {
        _conn = conn;
        _buffer = new byte[10];
        _size = 0;
        _pos = 0;
    }

    private Task<char> ReadNextChar()
    {
        Task readNext;
        if (_pos >= _size)
        {
            _pos = 0;
            readNext = Receive(_conn, _buffer, 0, _buffer.Length)
                .ContinueWith((Task<int> nrBytesRead) => {
                    _size = nrBytesRead.Result;
                });
        }
        else
        {
            readNext = Task.CompletedTask;
        }
        return readNext.ContinueWith((Task task) =>  {
            if (_size == 0)
            {
                // Console.WriteLine("At EOF");
                return '\0';
            }
            byte b = _buffer[_pos];
            ++_pos;
            char c = (char)b;
            if (c == '\n' || c == '\r' || c == '\t')
            {
                c = ' ';
            }
            else if (c != ' ' && (c < '0' || c > '9'))
            {
                Console.WriteLine("Unexpected caracter: {0} ({1})", c, ((byte)c));
                c = ' ';
            }
            // Console.WriteLine("Read char {0} ({1})", c, ((byte)c));
            return c;
        });
    }

    Task<int?> ReadNextInt()
    {
        Task<char> taskSkipWhitespace = TaskHelper.executeAsyncLoop(
                (char c) => { return c == ' ' || c == '\n'; }, (char c) => ReadNextChar(), ' ');
        Task<Tuple<int, int>> taskReadAndConvert = taskSkipWhitespace.ContinueWith((Task<char> firstChar) => {
            // In the following, for the second element of the tuple, bit 0 means continue reading, bit 1 means at least 1 char has been successfully read.
            Tuple<int, int> start = ((firstChar.Result != '\0') ? Tuple.Create<int, int>((firstChar.Result - '0'), 3) : Tuple.Create<int, int>(0, 0));
            return TaskHelper.executeAsyncLoop(
                (Tuple<int, int> current) => { return ((current.Item2 & 1) != 0); },
                (Tuple<int, int> current) => ReadNextChar().ContinueWith((Task<char> c) =>
                {
                    Tuple<int, int> ret = ((c.Result >= '0' && c.Result <= '9')
                        ? Tuple.Create<int, int>(current.Item1 * 10 + (c.Result - '0'), 3)
                        : Tuple.Create<int, int>(current.Item1, 2));
                    // Console.WriteLine("Returning tuple ({0}, {1})", ret.Item1, ret.Item2);
                    return ret;
                }),
                start);
        }).Unwrap();
        return taskReadAndConvert.ContinueWith((Task<Tuple<int, int>> lastVal) => ((lastVal.Result.Item2 & 2) != 0 ? (int?)(lastVal.Result.Item1) : (int?)(null)));
    }

    // Processes one request. Returns a Task that completes with true if the request is processed successfully, or false if the client closes the connection
    Task<bool> ProcessOneRequest()
    {
        return ReadNextInt()
            .ContinueWith((Task<int?> a) =>
            {
                if (a.Result.HasValue)
                {
                    return ReadNextInt().ContinueWith((Task<int?> b) => (b.Result.HasValue ? ((int?)(a.Result.Value + b.Result.Value)) : ((int?)(null))));
                }
                else
                {
                    return Task.FromResult((int?)(null));
                }
            }).Unwrap()
            .ContinueWith((Task<int?> sum) => {
                if(sum.Result.HasValue)
                {
                    return SendSum(sum.Result.Value).ContinueWith((Task t) => true);
                }
                else
                {
                    return Task.FromResult(false);
                }
            }).Unwrap();
    }

    private Task SendSum(int v)
    {
        string s = string.Format("{0}", v);
        byte[] b = new byte[s.Length + 1];
        for (int i = 0; i < s.Length; ++i)
        {
            b[i] = (byte)s[i];
        }
        b[s.Length] = 10;
        return Send(_conn, b, 0, b.Length);
    }

    private Task ProcessOneClient()
    {
        return TaskHelper.executeAsyncLoop(
            (bool b) => b,
            (bool b) => ProcessOneRequest(),
            true);
    }

    public static void Main(string[] args)
    {
        try
        {
            int port = Int32.Parse(args[0]);
            Console.WriteLine("Listening on port {0} ...", port);
            IPEndPoint listeningEndpoint = new IPEndPoint(IPAddress.Any, port);
            using (Socket listeningSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Unspecified))
            {
                listeningSocket.Bind(listeningEndpoint);
                listeningSocket.Listen(10);
                TaskHelper.executeAsyncLoop(
                    (bool b) => true,
                    (bool b) =>
                    {
                        Task<Socket> sock = Accept(listeningSocket);
                        sock.ContinueWith((Task<Socket> conn) => (new Session(conn.Result)).ProcessOneClient());
                        return sock.ContinueWith((Task<Socket> conn) => true);
                    },
                    true).Wait();
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine("Exception caught: {0}", ex);
        }
    }

    static Task<int> Receive(Socket conn, byte[] buf, int index, int count)
    {
        TaskCompletionSource<int> promise = new TaskCompletionSource<int>();
        conn.BeginReceive(buf, index, count, SocketFlags.None,
            (IAsyncResult ar) => {
                int bytesRead = conn.EndReceive(ar);
                // Console.WriteLine("Read {0} bytes", bytesRead);
                promise.SetResult(bytesRead);
            }, null);
        return promise.Task;
    }

    static Task<int> Send(Socket conn, byte[] buf, int index, int count)
    {
        TaskCompletionSource<int> promise = new TaskCompletionSource<int>();
        conn.BeginSend(buf, index, count, SocketFlags.None,
            (IAsyncResult ar) => promise.SetResult(conn.EndSend(ar)),
            null);
        return promise.Task;
    }

    static Task<Socket> Accept(Socket listeningSocket)
    {
        TaskCompletionSource<Socket> promise = new TaskCompletionSource<Socket>();
        listeningSocket.BeginAccept((IAsyncResult ar) => {
            Socket connSocket = listeningSocket.EndAccept(ar);
            Console.WriteLine(connSocket != null ? "Client connected" : "Accept() returned null");
            promise.SetResult(connSocket);
        }, null);
        return promise.Task;
    }

    private Socket _conn;
    private byte[] _buffer;
    private int _pos = 0;
    private int _size = 0;
}