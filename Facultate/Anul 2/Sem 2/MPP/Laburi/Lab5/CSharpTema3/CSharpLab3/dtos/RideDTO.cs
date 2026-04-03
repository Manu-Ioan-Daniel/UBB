namespace CSharpLab3.dtos;

public class RideDTO
{
    public int SeatNo { get; }
    public string ClientName { get; }

    public RideDTO(int seatNo, string clientName)
    {
        SeatNo = seatNo;
        ClientName = clientName;
    }
}