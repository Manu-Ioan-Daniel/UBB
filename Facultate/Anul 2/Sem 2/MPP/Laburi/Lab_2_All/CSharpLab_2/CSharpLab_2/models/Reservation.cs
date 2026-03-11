namespace CSharpLab_2.models;

public class Reservation : Entity<long>
{
    public required string ClientName { get; set; }
    public int ReservedSeatsCount { get; set; }
        
    // Înlocuit obiectul Ride cu RideId de tip long
    public long RideId { get; set; } 
}