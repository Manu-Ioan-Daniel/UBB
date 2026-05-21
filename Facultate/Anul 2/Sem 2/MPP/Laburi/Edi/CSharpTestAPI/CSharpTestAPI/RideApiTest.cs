using System.Text;
using System.Text.Json;

namespace CSharpTestAPI;

public class RideApiTests
{

    private readonly HttpClient _client = HttpClientFactory.Create();

    public async Task TestApi()
    {
        // POST
        var newRide = new
        {
            destination = "Bucuresti",
            rideDate = DateTime.Now.AddDays(1).ToString("yyyy-MM-dd"),   
            departureTime = "12:00",
            availableSeats = 3
        };

        var json = JsonSerializer.Serialize(newRide);

        var postResponse = await _client.PostAsync(
            "",
            new StringContent(json, Encoding.UTF8, "application/json")
        );

        var postBody = await postResponse.Content.ReadAsStringAsync();
        Console.WriteLine("POST RESULT: " + postBody);

        // GET
        var getResponse = await _client.GetAsync("?destination=Bucuresti");
        var getBody = await getResponse.Content.ReadAsStringAsync();
        Console.WriteLine("GET RESULT: " + getBody);

        // DELETE
        var deleteResponse = await _client.DeleteAsync("/api/rides/10");
        Console.WriteLine("DELETE STATUS: " + deleteResponse.StatusCode);
    }
}