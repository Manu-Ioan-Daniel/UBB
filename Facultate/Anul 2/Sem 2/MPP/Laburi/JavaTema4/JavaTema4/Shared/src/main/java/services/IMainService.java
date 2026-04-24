package services;

import dtos.RideDTO;
import models.Reservation;
import models.Ride;
import utils.Observer;

import java.time.LocalDate;
import java.util.List;

public interface IMainService {

    public List<RideDTO> findRides(LocalDate rideDate, String departureHour, String destination);
    public List<Ride> getAllRides();
    public void makeReservation(Long rideId, String text, int noSeats);
    public void cancelReservation(Long reservationId);
    public Ride getRide(Long rideId);
    public List<Reservation> findReservations();
    void addObserver(Observer observer);

}
