using CSharpLab_2.models;

namespace CSharpLab_2.repos;

public interface IReservationsRepo : IRepository<long, Reservation>
{
    IEnumerable<Reservation> FindReservationsByRide(long rideId);
}