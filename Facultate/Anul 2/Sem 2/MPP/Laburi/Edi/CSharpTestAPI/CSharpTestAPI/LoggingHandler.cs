
namespace CSharpTestAPI;

public class LoggingHandler : DelegatingHandler
{
    protected override async Task<HttpResponseMessage> SendAsync(
        HttpRequestMessage request,
        CancellationToken cancellationToken)
    {
        Console.WriteLine($"➡ REQUEST: {request.Method} {request.RequestUri}");

        if (request.Content != null)
        {
            var body = await request.Content.ReadAsStringAsync();
            Console.WriteLine($"➡ BODY: {body}");
        }

        var response = await base.SendAsync(request, cancellationToken);

        Console.WriteLine($"⬅ RESPONSE: {(int)response.StatusCode} {response.StatusCode}");

        return response;
    }
}