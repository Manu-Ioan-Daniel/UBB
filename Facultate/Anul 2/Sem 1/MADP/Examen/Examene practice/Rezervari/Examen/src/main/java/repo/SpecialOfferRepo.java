package repo;

import models.SpecialOffer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SpecialOfferRepo extends AbstractDbRepository<SpecialOffer> {

    @Override
    public SpecialOffer createEntity(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Long hotelId = rs.getLong("hotel_id");
        LocalDate startDate = rs.getDate("start_date").toLocalDate();
        LocalDate endDate = rs.getDate("end_date").toLocalDate();
        Double percents = rs.getDouble("discount_percentage");
        SpecialOffer specialOffer = new SpecialOffer(hotelId, startDate, endDate, percents);
        specialOffer.setId(id);
        return specialOffer;
    }

    @Override
    public PreparedStatement findOneStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement findAllStatement() throws SQLException {
        String sql = "SELECT * FROM special_offers";
        return connection.prepareStatement(sql);
    }

    @Override
    public PreparedStatement saveStatement(SpecialOffer entity) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement deleteStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement updateStatement(SpecialOffer entity) throws SQLException {
        return null;
    }

    public List<SpecialOffer> findSpecialOffersInTimeline(Long hotelId,LocalDate date) {
        String sql = """
                SELECT * FROM special_offers
                WHERE hotel_id = ? AND start_date <= ? AND end_date >= ?
                """;
        List<SpecialOffer> offers = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1,hotelId);
            ps.setDate(2,Date.valueOf(date));
            ps.setDate(3,Date.valueOf(date));

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                offers.add(createEntity(rs));
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return offers;
    }
}
