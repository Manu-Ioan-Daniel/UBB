
namespace CSharpTestAPI;

public static class HttpClientFactory
{
    public static HttpClient Create()
    {
        var handler = new LoggingHandler
        {
            InnerHandler = new HttpClientHandler()
        };

        return new HttpClient(handler)
        {
            BaseAddress = new Uri("http://localhost:8080/api/rides")
        };
    }
}