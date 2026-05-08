namespace CSharpLab_3.network;

public static class ServiceLocator
{
    public static ServicesObjectProxy Proxy { get; } =
        new ServicesObjectProxy("127.0.0.1", 5555);
}