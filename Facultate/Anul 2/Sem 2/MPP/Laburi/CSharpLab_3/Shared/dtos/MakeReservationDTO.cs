namespace Shared.dtos;

public class MakeReservationDTO
{
    public long RideId { get; set; }
    public string ClientName { get; set; }
    public int Seats { get; set; }

    public MakeReservationDTO(long rideId, string clientName, int seats)
    {
        RideId = rideId;
        ClientName = clientName;
        Seats = seats;
    }
}