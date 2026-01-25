package models;

import java.time.LocalDate;

public class Reservation extends Entity<Long> {
    private final Long clientId;
    private final Long hotelId;
    private final LocalDate startDate;
    private final int numberOfNights;

    public Reservation(Long clientId, Long hotelId, LocalDate startDate, int numberOfNights) {
        this.clientId = clientId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.numberOfNights = numberOfNights;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }
}
