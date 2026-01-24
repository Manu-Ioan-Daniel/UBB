package repo;

import models.Driver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DriverDbRepository extends AbstractDbRepository<Driver>{
    @Override
    public Driver createEntity(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");

        Driver driver = new Driver(name);
        driver.setId(id);
        return driver;
    }

    @Override
    public PreparedStatement findOneStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement findAllStatement() throws SQLException {
        String sql = "SELECT * FROM drivers";
        return connection.prepareStatement(sql);
    }

    @Override
    public PreparedStatement saveStatement(Driver entity) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement deleteStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement updateStatement(Driver entity) throws SQLException {
        return null;
    }


}
