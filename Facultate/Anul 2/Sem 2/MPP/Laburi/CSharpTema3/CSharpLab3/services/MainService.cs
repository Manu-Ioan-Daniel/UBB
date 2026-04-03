
using CSharpLab3.dtos;
using CSharpLab3.exceptions;
using CSharpLab3.models;
using CSharpLab3.repos;
using CSharpLab3.utils;
using NLog;

namespace CSharpLab3.services;

public class MainService : Observable
{
    private readonly IEmployeeRepo _employeeRepo;
    private readonly IRidesRepo _ridesRepo;
    private readonly IReservationsRepo _reservationsRepo;
    private static readonly Logger Logger = LogManager.GetCurrentClassLogger();

    public MainService(IEmployeeRepo employeeRepo, IRidesRepo ridesRepo, IReservationsRepo reservationsRepo)
    {
        _employeeRepo = employeeRepo;
        _ridesRepo = ridesRepo;
        _reservationsRepo = reservationsRepo;
    }

    public List<RideDTO> FindRides(DateOnly rideDate, string departureHour, string destination)
    {
        var departureTime = TimeOnly.Parse(departureHour);
        var foundRide = _ridesRepo.FindAll()
            .FirstOrDefault(r =>
                r.Date == rideDate &&
                r.DepartureTime == departureTime &&
                r.Destination == destination)
            ?? throw new ServiceException("Ride not found");

        var reservations = _reservationsRepo.FindReservationsByRide(foundRide.Id);        
        var result = new List<RideDTO>();
        int seatNo = 1;

        foreach (var reservation in reservations)
        {
            for (int i = 0; i < reservation.ReservedSeatsCount; i++)
                result.Add(new RideDTO(seatNo++, reservation.ClientName));
        }

        while (seatNo <= foundRide.AvailableSeats)
            result.Add(new RideDTO(seatNo++, "-"));

        return result;
    }

    public List<Ride> GetAllRides() => _ridesRepo.FindAll().ToList();

    public void MakeReservation(long rideId, string clientName, int noSeats)
    {
        var reservation = new Reservation
        {
            ClientName = clientName,
            ReservedSeatsCount = noSeats,
            RideId = rideId
        };
        _reservationsRepo.Save(reservation);

        var ride = _ridesRepo.FindOne(rideId);
        if (ride != null)
        {
            ride.AvailableSeats -= noSeats;
            _ridesRepo.Update(ride);
        }

        NotifyObservers();
        Logger.Info($"Reservation made: {clientName}, {noSeats} seats, ride {rideId}");
    }

    public void CancelReservation(long reservationId)
    {
        var reservation = _reservationsRepo.FindOne(reservationId);
        if (reservation == null) return;

        var ride = _ridesRepo.FindOne(reservation.RideId);
        if (ride != null)
        {
            ride.AvailableSeats += reservation.ReservedSeatsCount;
            _ridesRepo.Update(ride);
        }

        _reservationsRepo.Delete(reservationId);
        NotifyObservers();
        Logger.Info($"Reservation {reservationId} cancelled");
    }

    public Ride? GetRide(long rideId) => _ridesRepo.FindOne(rideId);

    public List<Reservation> FindReservations() => _reservationsRepo.FindAll().ToList();
}