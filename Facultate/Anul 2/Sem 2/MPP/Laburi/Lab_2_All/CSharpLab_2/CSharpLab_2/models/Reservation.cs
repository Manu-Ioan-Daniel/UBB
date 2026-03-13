namespace CSharpLab_2.models;

public class Reservation : Entity<long>
{
    public required string ClientName { get; init; }
    public required int ReservedSeatsCount { get; init; }
    public required long RideId { get; init; } 
}