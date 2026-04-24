namespace Shared.dtos;

public class CancelReservationDTO
{
    public long ReservationId { get; set; }

    public CancelReservationDTO(long reservationId)
    {
        ReservationId = reservationId;
    }
}