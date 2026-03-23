using CSharpLab3.models;

namespace CSharpLab3.repos;

public interface IReservationsRepo : IRepository<long, Reservation>
{
    IEnumerable<Reservation> FindReservationsByRide(long rideId);
}