package dtos;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;

public class MakeReservationDTO implements Serializable {

    @SerializedName("RideId")
    private final Long rideId;
    @SerializedName("ClientName")
    private final String clientName;
    @SerializedName("Seats")
    private final int seats;

    public MakeReservationDTO(Long rideId, String clientName, int seats) {
        this.rideId = rideId;
        this.clientName = clientName;
        this.seats = seats;
    }

    public Long getRideId() {
        return rideId;
    }
    public String getClientName() {
        return clientName;
    }
    public int getSeats() {
        return seats;
    }
}
