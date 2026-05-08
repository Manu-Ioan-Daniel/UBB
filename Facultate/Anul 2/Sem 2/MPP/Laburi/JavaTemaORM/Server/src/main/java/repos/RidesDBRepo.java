package repos;

import models.Ride;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class RidesDBRepo implements RidesRepo {

    private final JdbcUtils dbUtils;
    private final Logger logger = LogManager.getLogger();

    public RidesDBRepo(Properties properties) {
        logger.info("Constructing RidesDBRepo with properties: {} ", properties);
        this.dbUtils = new JdbcUtils(properties);
    }

    /**
     * Find a ride by its ID
     * @param id the ID of the ride to find
     * @return an Optional containing the found Ride, or an empty Optional if no ride with the given ID exists
     */
    @Override
    public Optional<Ride> findOne(Long id) {

        String sql = "SELECT * FROM rides WHERE id = ?";
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Ride ride = rideFromResultSet(rs);
                logger.info("Ride found with id {}: {}", id, ride);
                return Optional.of(ride);

            } else {
                logger.info("No ride found with id {}", id);
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error(e);
            return Optional.empty();
        }
    }

    /**
     * Find all rides in the database
     * @return a list of all rides found in the database
     */
    @Override
    public List<Ride> findAll() {

        String sql = "SELECT * FROM rides";
        List<Ride> rides = new ArrayList<>();
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ride ride = rideFromResultSet(rs);
                rides.add(ride);
            }
            logger.info("Found {} rides in the database", rides.size());

        } catch (SQLException e) {
            logger.error(e);
        }

        return rides;
    }

    /**
     * Save a new ride to the database
     * @param ride the Ride object to be saved to the database
     */
    @Override
    public void save(Ride ride) {
        String sql = "INSERT INTO rides (destination, ride_date, departure_time, available_seats) VALUES (?, ?, ?, ?)";
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ride.getDestination());
            ps.setDate(2, Date.valueOf(ride.getDate()));
            ps.setTime(3, Time.valueOf(ride.getDepartureTime()));
            ps.setInt(4, ride.getAvailableSeats());
            ps.executeUpdate();
            logger.info("Ride saved successfully: {}", ride);
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Delete a ride from the database by its ID
     * @param id the ID of the ride to be deleted from the database
     */
    @Override
    public void delete(Long id) {

        String sql = "DELETE FROM rides WHERE id = ?";
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            logger.info("Ride with id {} deleted successfully", id);

        } catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Update an existing ride in the database
     * @param ride the Ride with the id of the ride to be updated and the new values for the ride's fields
     */
    @Override
    public void update(Ride ride) {

        String sql = "UPDATE rides SET destination = ?, ride_date = ?, departure_time = ?, available_seats = ? WHERE id = ?";
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, ride.getDestination());
            ps.setDate(2, Date.valueOf(ride.getDate()));
            ps.setTime(3, Time.valueOf(ride.getDepartureTime()));
            ps.setInt(4, ride.getAvailableSeats());
            ps.setLong(5, ride.getId());
            ps.executeUpdate();

            logger.info("Ride updated successfully: {}", ride);

        } catch (SQLException e) {
            logger.error(e);
        }
    }

    private Ride rideFromResultSet(ResultSet rs) throws SQLException {

        String destination = rs.getString("destination");
        Date rideDate = rs.getDate("ride_date");
        Time departureTime = rs.getTime("departure_time");
        Integer availableSeats = rs.getInt("available_seats");

        Ride ride = new Ride(destination, rideDate.toLocalDate(), departureTime.toLocalTime());
        ride.setAvailableSeats(availableSeats);
        ride.setId(rs.getLong("id"));

        return ride;
    }
}
