package models;

public class Reservation extends Entity<Long>{
    
    private String clientName;
    private Integer reservedSeatsCount;
    private Long rideId;

    protected Reservation() {
        this.clientName = "";
        this.reservedSeatsCount = 0;
        this.rideId = 0L;
    }

    public Reservation(String clientName, Integer reservedSeatsCount, Long rideId) {
        this.clientName = clientName;
        this.reservedSeatsCount = reservedSeatsCount;
        this.rideId = rideId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getReservedSeatsCount() {
        return reservedSeatsCount;
    }

    public void setReservedSeatsCount(Integer reservedSeatsCount) {
        this.reservedSeatsCount = reservedSeatsCount;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
}
