using MySql.Data.MySqlClient;
using NLog;
using Server.utils;
using Shared.models;

namespace Server.repos;

public class RidesDbRepo: IRidesRepo
{
    private readonly DbUtils _dbUtils;
    private static readonly Logger Logger = LogManager.GetCurrentClassLogger();

    public RidesDbRepo(DbUtils dbUtils)
    {
        _dbUtils = dbUtils;
        Logger.Info("Rides Repo created succesfully");
    }

    /// <summary>
    /// Finds a ride by its ID.
    /// </summary>
    /// <param name="id">The ride's ID</param>
    /// <returns>The ride with the specified ID or null if he isn't found</returns>
    public Ride? FindOne(long id)
    {
        const string sql = "SELECT * FROM rides WHERE id = @id";

        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        cmd.Parameters.AddWithValue("@id", id);
        using var reader = cmd.ExecuteReader();
        if (reader.Read())
        {
            var ride = RideFromReader(reader);
            Logger.Info("Ride found: " + ride);
            return ride;
        }

        Logger.Info("Ride not found: " + id);
        return null;
    }

    /// <summary>
    /// Finds all rides in the database.
    /// </summary>
    /// <returns>Anything that can be iterated on containing all rides</returns>
    public IEnumerable<Ride> FindAll()
    {
        const string sql = "SELECT * FROM rides";

        var rides = new List<Ride>();
        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        using var reader = cmd.ExecuteReader();

        while (reader.Read())
        {
            rides.Add(RideFromReader(reader));
        }

        Logger.Info("Rides found: " + rides.Count);
        return rides;
    }

    /// <summary>
    /// Saves a ride to the database
    /// </summary>
    /// <param name="ride">The ride we are supposed to save</param>
    public void Save(Ride ride)
    {
        const string sql = "INSERT INTO rides (destination, ride_date, departure_time, available_seats) VALUES (@destination,@date,@departureTime,@availableSeats)";

        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);

        cmd.Parameters.AddWithValue("@destination", ride.Destination);
        cmd.Parameters.AddWithValue("@date", ride.Date.ToDateTime(TimeOnly.MinValue));
        cmd.Parameters.AddWithValue("@departureTime", ride.DepartureTime.ToTimeSpan());
        cmd.Parameters.AddWithValue("@availableSeats", ride.AvailableSeats);
        cmd.ExecuteNonQuery();
        Logger.Info("Ride saved: Destination = " + ride.Destination);
    }

    /// <summary>
    /// Deletes the ride with the specified id
    /// </summary>
    /// <param name="id">The id of the ride we want to delete</param>
    public void Delete(long id)
    {
        const string sql = "DELETE FROM rides WHERE id = @id";

        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);

        cmd.Parameters.AddWithValue("@id", id);
        cmd.ExecuteNonQuery();
        Logger.Info("Ride with ID = " + id + "deleted");
    }

    /// <summary>
    /// Updates a ride in the database. The ride is identified by its id and all other fields are updated to the values of the given ride
    /// </summary>
    /// <param name="ride">The ride with new data,and the id of the ride we want to update</param>
    public void Update(Ride ride)
    {
        const string sql = "UPDATE rides SET destination = @destination, ride_date = @date, departure_time = @departureTime, available_seats = @availableSeats WHERE id = @id";

        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);

        cmd.Parameters.AddWithValue("@id", ride.Id);
        cmd.Parameters.AddWithValue("@destination", ride.Destination);
        cmd.Parameters.AddWithValue("@date", ride.Date.ToDateTime(TimeOnly.MinValue));
        cmd.Parameters.AddWithValue("@departureTime", ride.DepartureTime.ToTimeSpan());
        cmd.Parameters.AddWithValue("@availableSeats", ride.AvailableSeats);
        cmd.ExecuteNonQuery();
        Logger.Info("Ride updated: Destination = " + ride.Destination);
    }

    /// <summary>
    /// Helper method that creates a Ride object from a MySqlDataReader
    /// </summary>
    /// <param name="reader"> A MySqlDataReader containing all ride information</param>
    /// <returns>The ride that results from the MySqlDataReader</returns>
    private static Ride RideFromReader(MySqlDataReader reader)
    {
        return new Ride
        {
            Id = reader.GetInt64("id"),
            Destination = reader.GetString("destination"),
            Date = DateOnly.FromDateTime(reader.GetDateTime("ride_date")),
            DepartureTime = TimeOnly.FromTimeSpan(reader.GetTimeSpan("departure_time")),
            AvailableSeats = reader.GetInt32("available_seats")
        };
    }
}

