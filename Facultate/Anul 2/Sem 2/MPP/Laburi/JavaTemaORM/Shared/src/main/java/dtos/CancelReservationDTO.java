package dtos;

import java.io.Serializable;

public class CancelReservationDTO implements Serializable {

    private final Long reservationId;

    public CancelReservationDTO(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getReservationId() {
        return reservationId;
    }
}
