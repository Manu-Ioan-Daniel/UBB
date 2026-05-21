namespace CSharpTestAPI;

public class Program
{
    public static async Task Main(string[] args)
    {
        var test = new RideApiTests();
        await test.TestApi();
    }
}