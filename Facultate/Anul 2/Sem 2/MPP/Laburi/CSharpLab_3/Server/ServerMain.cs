using Server.server;

namespace Server;

public class Program
{
    public static void Main(string[] args)
    {
        try
        {
            int port = 5555;

            var server = new ObjectConcurrentServer(port);
            server.Start();
        }
        catch (Exception)
        {
            Environment.Exit(1);
        }
    }
}