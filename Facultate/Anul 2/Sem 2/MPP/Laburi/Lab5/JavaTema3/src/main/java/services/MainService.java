package services;

import models.Reservation;
import models.Ride;
import repos.EmployeeDBRepo;
import repos.ReservationsDBRepo;
import repos.RidesDBRepo;
import dtos.RideDTO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import exceptions.ServiceException;
import utils.Observable;

public class MainService extends Observable {

    private final EmployeeDBRepo employeeDBRepo;
    private final RidesDBRepo ridesDBRepo;
    private final ReservationsDBRepo reservationsDBRepo;

    public MainService(EmployeeDBRepo employeeDBRepo, RidesDBRepo ridesDBRepo, ReservationsDBRepo reservationsDBRepo) {
        this.employeeDBRepo = employeeDBRepo;
        this.ridesDBRepo = ridesDBRepo;
        this.reservationsDBRepo = reservationsDBRepo;
    }


    public List<RideDTO> findRides(LocalDate rideDate, String departureHour, String destination) {
        List<Ride> rides = ridesDBRepo.findAll();
        Ride foundRide = rides.stream()
                .filter(ride -> ride.getDate().equals(rideDate) &&
                        ride.getDepartureTime().equals(LocalTime.parse(departureHour, DateTimeFormatter.ofPattern("HH:mm"))) &&
                        ride.getDestination().equals(destination))

                .findFirst()
                .orElse(null);
        if (foundRide == null) {
            throw new ServiceException("Ride not found");
        }
        List<Reservation> reservations = reservationsDBRepo.findReservationsByRideId(foundRide.getId());
        List<RideDTO> rideDTOList = new ArrayList<>();
        int seatNo = 1;
        for (Reservation reservation : reservations) {
            Integer seatsCount = reservation.getReservedSeatsCount();
            while (seatsCount > 0) {
                rideDTOList.add(new RideDTO(seatNo, reservation.getClientName()));
                seatNo++;
                seatsCount--;
            }
        }
        while (seatNo <= foundRide.getAvailableSeats()) {
            rideDTOList.add(new RideDTO(seatNo, "-"));
            seatNo++;
        }
        return rideDTOList;
    }

    public List<Ride> getAllRides() {
        return ridesDBRepo.findAll();
    }

    public void makeReservation(Long rideId, String text, int noSeats) {
        Reservation reservation = new Reservation(text, noSeats, rideId);
        reservationsDBRepo.save(reservation);
        Ride ride = ridesDBRepo.findOne(rideId).orElse(null);
        if(ride != null) {
            ride.setAvailableSeats(ride.getAvailableSeats() - noSeats);
            ridesDBRepo.update(ride);
        }
        notifyObservers();
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationsDBRepo.findOne(reservationId).orElse(null);
        if (reservation != null) {
            ridesDBRepo.findOne(reservation.getRideId()).ifPresent(ride -> {
                ride.setAvailableSeats(ride.getAvailableSeats() + reservation.getReservedSeatsCount());
                ridesDBRepo.update(ride);
            });
            reservationsDBRepo.delete(reservationId);
            notifyObservers();
        }
    }

    public Ride getRide(Long rideId) {
        return ridesDBRepo.findOne(rideId).orElse(null);
    }

    public List<Reservation> findReservations() {
        return reservationsDBRepo.findAll();
    }
}
