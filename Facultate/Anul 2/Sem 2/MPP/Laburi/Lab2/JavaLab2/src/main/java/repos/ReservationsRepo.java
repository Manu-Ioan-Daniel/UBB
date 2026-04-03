package repos;

import models.Reservation;

import java.util.List;

public interface ReservationsRepo extends Repository<Reservation, Long> {

    List<Reservation> findReservationsByRideId(Long rideId);
}
