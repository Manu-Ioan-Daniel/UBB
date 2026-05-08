package dtos;

import java.io.Serializable;

public class MakeReservationDTO implements Serializable {

    private final Long rideId;
    private final String text;
    private final int noSeats;

    public MakeReservationDTO(Long rideId, String text, int noSeats) {
        this.rideId = rideId;
        this.text = text;
        this.noSeats = noSeats;
    }

    public Long getRideId() {
        return rideId;
    }
    public String getText() {
        return text;
    }
    public int getNoSeats() {
        return noSeats;
    }
}
