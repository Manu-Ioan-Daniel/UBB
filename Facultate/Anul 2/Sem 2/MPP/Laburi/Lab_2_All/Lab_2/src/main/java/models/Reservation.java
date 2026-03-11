package models;

public class Reservation extends Entity<Long>{
    
    private final String clientName;
    private final Integer reservedSeatsCount;
    private final Long rideId;

    public Reservation(String clientName, Integer reservedSeatsCount, Long rideId) {
        this.clientName = clientName;
        this.reservedSeatsCount = reservedSeatsCount;
        this.rideId = rideId;
    }

    public String getClientName() {
        return clientName;
    }

    public Integer getReservedSeatsCount() {
        return reservedSeatsCount;
    }

    public Long getRideId() {
        return rideId;
    }
}
