using Shared.dtos;
using Shared.models;
using Shared.utils;

namespace Shared.services;

public interface IMainService
{
    List<RideDTO> FindRides(DateOnly rideDate, string departureHour, string destination);
    List<Ride> GetAllRides();
    void MakeReservation(long rideId, string clientName, int noSeats);
    void CancelReservation(long reservationId);
    Ride? GetRide(long rideId);
    List<Reservation> FindReservations();

    void AddObserver(IObserver observer);
    void RemoveObserver(IObserver observer);
}