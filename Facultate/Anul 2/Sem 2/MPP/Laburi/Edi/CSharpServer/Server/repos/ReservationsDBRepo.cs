using MySql.Data.MySqlClient;
using NLog;
using Server.utils;
using Shared.models;

namespace Server.repos;

public class ReservationsDbRepo: IReservationsRepo
{
    private readonly DbUtils _dbUtils;
    private static readonly Logger Logger = LogManager.GetCurrentClassLogger();

    public ReservationsDbRepo(DbUtils dbUtils)
    {
        _dbUtils = dbUtils;
        Logger.Info("Reservations Repo created succesfully");
    }

    /// <summary>
    /// Finds a reservation by its ID
    /// </summary>
    /// <param name="id">The reservation's ID</param>
    /// <returns>The reservation with the specified ID or null if it isn't found</returns>
    public Reservation? FindOne(long id)
    {
        const string sql = "SELECT * FROM reservations WHERE id = @id";

        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        cmd.Parameters.AddWithValue("@id", id);
        using var reader = cmd.ExecuteReader();
        if (reader.Read())
        {
            var reservation = ReservationFromReader(reader);
            Logger.Info("Reservation found: " + reservation);
            return reservation;
        }

        Logger.Info("Reservation not found: " + id);
        return null;
    }

    /// <summary>
    /// Finds all reservations in the database
    /// </summary>
    /// <returns>Anything that can be iterated on containing all reservations</returns>
    public IEnumerable<Reservation> FindAll()
    {
        const string sql = "SELECT * FROM reservations";

        var reservations = new List<Reservation>();
        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        using var reader = cmd.ExecuteReader();

        while (reader.Read())
        {
            reservations.Add(ReservationFromReader(reader));
        }

        Logger.Info("Reservations found: " + reservations.Count);
        return reservations;
    }

    /// <summary>
    /// Finds all reservations for a ride in the database.
    /// </summary>
    /// <param name="rideId">The ride's ID</param>
    /// <returns>Anything that can be iterated on containing all reservations with the specified ride ID</returns>
    public IEnumerable<Reservation> FindReservationsByRide(long rideId)
    {
        const string sql = "SELECT * FROM reservations WHERE ride_id = @rideId";

        var reservations = new List<Reservation>();
        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);
        cmd.Parameters.AddWithValue("@rideId", rideId);
        using var reader = cmd.ExecuteReader();

        while (reader.Read())
        {
            reservations.Add(ReservationFromReader(reader));
        }

        Logger.Info("Reservations found for ride " + rideId + ": " + reservations.Count);
        return reservations;
    }

    /// <summary>
    /// Saves a reservation to the database
    /// </summary>
    /// <param name="reservation">The reservation we are supposed to save</param>
    public void Save(Reservation reservation)
    {
        const string sql = "INSERT INTO reservations (client_name, reserved_seats_count, ride_id) VALUES (@clientName,@reservedSeatsCount,@rideId)";

        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);

        cmd.Parameters.AddWithValue("@clientName", reservation.ClientName);
        cmd.Parameters.AddWithValue("@reservedSeatsCount", reservation.ReservedSeatsCount);
        cmd.Parameters.AddWithValue("@rideId", reservation.RideId);
        cmd.ExecuteNonQuery();
        Logger.Info("Reservation saved: Client Name = " + reservation.ClientName);
    }

    /// <summary>
    /// Deletes the reservation with the specified id
    /// </summary>
    /// <param name="id">The id of the reservation we want to delete</param>
    public void Delete(long id)
    {
        const string sql = "DELETE FROM reservations WHERE id = @id";

        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);

        cmd.Parameters.AddWithValue("@id", id);
        cmd.ExecuteNonQuery();
        Logger.Info("Reservation with ID = " + id + "deleted");
    }

    /// <summary>
    /// Updates a reservation in the database. The reservation is identified by its id and all other fields are updated to the values of the given reservation
    /// </summary>
    /// <param name="reservation">The reservation with new data,and the id of the reservation we want to update</param>
    public void Update(Reservation reservation)
    {
        const string sql = "UPDATE reservations SET client_name = @clientName, reserved_seats_count = @reservedSeatsCount, ride_id = @rideId WHERE id = @id";

        using var conn = _dbUtils.GetConnection();
        using var cmd = new MySqlCommand(sql, conn);

        cmd.Parameters.AddWithValue("@id", reservation.Id);
        cmd.Parameters.AddWithValue("@clientName", reservation.ClientName);
        cmd.Parameters.AddWithValue("@reservedSeatsCount", reservation.ReservedSeatsCount);
        cmd.Parameters.AddWithValue("@rideId", reservation.RideId);
        cmd.ExecuteNonQuery();
        Logger.Info("Reservation updated: Client Name = " + reservation.ClientName);
    }

    /// <summary>
    /// Helper method that creates a Reservation object from a MySqlDataReader
    /// </summary>
    /// <param name="reader"> A MySqlDataReader containing all reservation information</param>
    /// <returns>The reservation that results from the MySqlDataReader</returns>
    private static Reservation ReservationFromReader(MySqlDataReader reader)
    {
        return new Reservation
        {
            Id = reader.GetInt64("id"),
            ClientName = reader.GetString("client_name"),
            ReservedSeatsCount = reader.GetInt32("reserved_seats_count"),
            RideId = reader.GetInt64("ride_id")
        };
    }
}

