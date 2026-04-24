using Shared.models;

namespace Server.repos;

public interface IReservationsRepo : IRepository<long, Reservation>
{
    IEnumerable<Reservation> FindReservationsByRide(long rideId);
}