package dtos;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;

public class CancelReservationDTO implements Serializable {

    @SerializedName("ReservationId")
    private final Long reservationId;

    public CancelReservationDTO(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getReservationId() {
        return reservationId;
    }
}
