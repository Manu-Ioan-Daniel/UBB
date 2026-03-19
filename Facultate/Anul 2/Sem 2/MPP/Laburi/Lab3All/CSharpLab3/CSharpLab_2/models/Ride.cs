namespace CSharpLab_2.models;

public class Ride : Entity<long>
{
    public required string Destination { get; init; }
    public required DateOnly Date { get; init; }
    public required TimeOnly DepartureTime { get; init; }
    public int AvailableSeats { get; set; } = 18;
}