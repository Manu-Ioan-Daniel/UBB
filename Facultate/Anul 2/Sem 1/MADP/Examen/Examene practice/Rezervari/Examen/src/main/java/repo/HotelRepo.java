package repo;

import enums.HotelType;
import models.Hotel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelRepo extends AbstractDbRepository<Hotel>{

    @Override
    public Hotel createEntity(ResultSet rs) throws SQLException {

        Long id = rs.getLong("id");
        Long locationId = rs.getLong("location_id");
        String hotelName = rs.getString("hotel_name");
        int noRooms = rs.getInt("no_rooms");
        double pricePerNight = rs.getDouble("price_per_night");
        String typeString = rs.getString("type");
        HotelType type =HotelType.valueOf(typeString);
        Hotel hotel = new Hotel(locationId, hotelName, noRooms, pricePerNight, type);
        hotel.setId(id);

        return hotel;
    }

    @Override
    public PreparedStatement findOneStatement(Long id) throws SQLException {
        String sql = "SELECT * FROM hotels WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement findAllStatement() throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement saveStatement(Hotel entity) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement deleteStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement updateStatement(Hotel entity) throws SQLException {
        return null;
    }

    public List<Hotel> findByLocation(String locationName) {

        String sql = """
                SELECT * FROM hotels h
                JOIN locations l ON h.location_id = l.id
                WHERE l.name = ?
        """;

        List<Hotel> hotels = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, locationName);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                hotels.add(createEntity(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return hotels;
    }

    public boolean isReserved(Long hotelId) {
        String sql = """
                SELECT 1 FROM reservations r
                WHERE r.hotel_id = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, hotelId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
