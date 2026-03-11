package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ride extends Entity<Long>{

    private final String destination;
    private final LocalDate date;
    private final LocalTime departureTime;
    private Integer availableSeats = 18;

    public Ride(String destination, LocalDate date, LocalTime departureTime) {
        this.destination = destination;
        this.date = date;
        this.departureTime = departureTime;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
}
