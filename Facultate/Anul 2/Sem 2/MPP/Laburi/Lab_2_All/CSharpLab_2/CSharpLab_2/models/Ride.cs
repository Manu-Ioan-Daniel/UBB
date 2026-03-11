namespace CSharpLab_2.models;

public class Ride : Entity<long>
{
    public required string Destination { get; set; }
    public DateOnly Date { get; set; }
    public TimeOnly DepartureTime { get; set; }
    public int AvailableSeats { get; set; } = 18;
}