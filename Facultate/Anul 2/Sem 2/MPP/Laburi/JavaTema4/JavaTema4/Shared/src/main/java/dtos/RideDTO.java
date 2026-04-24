package dtos;

public class RideDTO {

    private final int noSeats;
    private final String reservedBy;

    public RideDTO(int noSeats, String reservedBy) {
        this.noSeats = noSeats;
        this.reservedBy = reservedBy;
    }

    public int getNoSeats() {
        return noSeats;
    }

    public String getReservedBy() {
        return reservedBy;
    }

}
