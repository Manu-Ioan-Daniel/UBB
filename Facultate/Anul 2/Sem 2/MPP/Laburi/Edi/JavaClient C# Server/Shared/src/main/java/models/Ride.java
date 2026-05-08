package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ride extends Entity<Long>{

    private String destination;
    private LocalDate date;
    private LocalTime departureTime;
    private Integer availableSeats = 18;

    protected Ride() {
        this.destination = "";
        this.date = LocalDate.now();
        this.departureTime = LocalTime.MIDNIGHT;
    }

    public Ride(String destination, LocalDate date, LocalTime departureTime) {
        this.destination = destination;
        this.date = date;
        this.departureTime = departureTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
}
