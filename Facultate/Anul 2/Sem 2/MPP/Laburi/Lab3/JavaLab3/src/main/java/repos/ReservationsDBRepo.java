package repos;

import models.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ReservationsDBRepo implements ReservationsRepo {

    private final JdbcUtils dbUtils;
    private final Logger logger = LogManager.getLogger();

    public ReservationsDBRepo(Properties properties) {
        logger.info("Constructing ReservationsDBRepo with properties: {} ", properties);
        this.dbUtils = new JdbcUtils(properties);
    }

    /**
     * Finds a Reservation in the database by its ID
     * @param id the ID of the Reservation to find
     * @return an Optional containing the Reservation if found, or an empty Optional if not found or if there was an error accessing the database
     */
    @Override
    public Optional<Reservation> findOne(Long id) {
        String sql = "SELECT * FROM reservations WHERE id = ?";
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Reservation reservation = reservationFromResultSet(rs);
                logger.info("Reservation found with id {}: {}", id, reservation);
                return Optional.of(reservation);
            } else {
                logger.info("No reservation found with id {}", id);
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error(e);
            return Optional.empty();
        }
    }

    /**
     * Finds all Reservations in the database
     * @return a List of all Reservations found in the database, or an empty List if there was an error accessing the database
     */
    @Override
    public List<Reservation> findAll() {

        String sql = "SELECT * FROM reservations";
        List<Reservation> reservations = new ArrayList<>();
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reservation reservation = reservationFromResultSet(rs);
                reservations.add(reservation);
            }

            logger.info("Found {} reservations in the database", reservations.size());

        } catch (SQLException e) {
            logger.error(e);
        }

        return reservations;
    }

    /**
     * Saves a new Reservation to the database
     * @param reservation the Reservation to save
     */
    @Override
    public void save(Reservation reservation) {

        String sql = "INSERT INTO reservations (client_name, reserved_seats_count, ride_id) VALUES (?, ?, ?)";
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reservation.getClientName());
            ps.setInt(2, reservation.getReservedSeatsCount());
            ps.setLong(3, reservation.getRideId());
            ps.executeUpdate();
            logger.info("Reservation saved successfully: {}", reservation);

        } catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Deletes a Reservation from the database by its ID
     * @param id the ID of the Reservation to delete
     */
    @Override
    public void delete(Long id) {

        String sql = "DELETE FROM reservations WHERE id = ?";
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            logger.info("Reservation with id {} deleted successfully", id);

        } catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Updates an existing Reservation in the database
     * @param reservation the Reservation with updated data and the id of the Reservation to update
     */
    @Override
    public void update(Reservation reservation) {

        String sql = "UPDATE reservations SET client_name = ?, reserved_seats_count = ?, ride_id = ? WHERE id = ?";
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reservation.getClientName());
            ps.setInt(2, reservation.getReservedSeatsCount());
            ps.setLong(3, reservation.getRideId());
            ps.setLong(4, reservation.getId());
            ps.executeUpdate();
            logger.info("Reservation updated successfully: {}", reservation);

        } catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Finds all Reservations in the database for a given ride ID
     * @param rideId the ID of the ride to find Reservations for
     * @return a List of Reservations for the given ride ID, or an empty List if there was an error accessing the database
     */
    @Override
    public List<Reservation> findReservationsByRideId(Long rideId) {

        String sql = "SELECT * FROM reservations WHERE ride_id = ?";
        List<Reservation> reservations = new ArrayList<>();
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, rideId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reservation reservation = reservationFromResultSet(rs);
                reservations.add(reservation);
            }
            logger.info("Found {} reservations for ride id {}", reservations.size(), rideId);

        } catch (SQLException e) {
            logger.error(e);
        }

        return reservations;
    }

    /**
     * Helper method to create a Reservation object from a ResultSet
     * @param rs the ResultSet containing the data for a Reservation
     * @return a Reservation object created from the ResultSet
     * @throws SQLException if there was an error accessing the data in the ResultSet
     */
    private Reservation reservationFromResultSet(ResultSet rs) throws SQLException {

        String clientName = rs.getString("client_name");
        Integer reservedSeatsCount = rs.getInt("reserved_seats_count");
        Long rideId = rs.getLong("ride_id");

        Reservation reservation = new Reservation(clientName, reservedSeatsCount, rideId);
        reservation.setId(rs.getLong("id"));

        return reservation;
    }
}
