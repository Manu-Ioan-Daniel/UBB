package repo;

import models.Reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReservationRepo extends AbstractDbRepository<Reservation> {

    @Override
    public Reservation createEntity(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement findOneStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement findAllStatement() throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement saveStatement(Reservation entity) throws SQLException {
        String sql = """
                INSERT INTO reservations (client_id, hotel_id, start_date, number_of_nights)
                VALUES (?, ?, ?, ?)
                """;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, entity.getClientId());
        ps.setLong(2, entity.getHotelId());
        ps.setDate(3, Date.valueOf(entity.getStartDate()));
        ps.setInt(4, entity.getNumberOfNights());
        return ps;
    }

    @Override
    public PreparedStatement deleteStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement updateStatement(Reservation entity) throws SQLException {
        return null;
    }
}
