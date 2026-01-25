package repo;

import models.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRepo extends AbstractDbRepository<Location>{

    @Override
    public Location createEntity(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String locationName = rs.getString("name");
        Location location = new Location(locationName);
        location.setId(id);
        return location;
    }

    @Override
    public PreparedStatement findOneStatement(Long id) throws SQLException {
        String sql = "SELECT * FROM locations WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement findAllStatement() throws SQLException {

        String sql = "SELECT * FROM locations";
        return connection.prepareStatement(sql);

    }

    @Override
    public PreparedStatement saveStatement(Location entity) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement deleteStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement updateStatement(Location entity) throws SQLException {
        return null;
    }
}
