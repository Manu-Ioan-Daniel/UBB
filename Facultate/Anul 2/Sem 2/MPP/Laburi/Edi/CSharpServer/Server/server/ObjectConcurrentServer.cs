using System.Net;
using System.Net.Sockets;

namespace Server.server;

public class ObjectConcurrentServer
{
    private readonly int _port;
    private volatile bool _running;
    private TcpListener _server;

    public ObjectConcurrentServer(int port)
    {
        _port = port;
    }

    public void Start()
    {
        _server = new TcpListener(IPAddress.Any, _port);
        _server.Start();
        _running = true;

        try
        {
            while (_running)
            {
                var client = _server.AcceptSocket();

                var worker = new ClientObjectWorker(client);

                var thread = new Thread(() =>
                {
                    try
                    {
                        worker.Run();
                    }
                    catch (Exception ex)
                    {
                        Console.WriteLine("WORKER CRASH: " + ex);
                    }
                });

                thread.IsBackground = true;
                thread.Start();
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine("SERVER LOOP CRASH: " + ex);
        }
    }

    public void Stop()
    {
        _running = false;
        _server.Stop();
    }
}