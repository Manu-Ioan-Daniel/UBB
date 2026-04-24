namespace Shared.models;

public class Reservation : Entity<long>
{
    public required string ClientName { get; init; }
    public required int ReservedSeatsCount { get; init; }
    public required long RideId { get; init; }

    public override string ToString()
    {
        return $"Reservation: Client Name = {ClientName}, Reserved Seats Count = {ReservedSeatsCount}, Ride ID = {RideId}";
    }
}