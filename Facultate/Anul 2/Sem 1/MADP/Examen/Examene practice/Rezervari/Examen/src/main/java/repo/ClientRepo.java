package repo;

import enums.Hobby;
import models.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ClientRepo extends AbstractDbRepository<Client>{

    @Override
    public Client createEntity(ResultSet rs) throws SQLException {

        Long id = rs.getLong("id");
        String name = rs.getString("name");
        int fidelityGrade = rs.getInt("fidelityGrade");
        int age = rs.getInt("age");
        String hobbiesStr = rs.getString("hobbies");
        List<Hobby> hobbies = Arrays.stream(hobbiesStr.split(","))
                .map(Hobby::valueOf)
                .toList();
        Client client = new Client(name, fidelityGrade, age, hobbies);
        client.setId(id);
        return client;

    }

    @Override
    public PreparedStatement findOneStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement findAllStatement() throws SQLException {

        String sql = "SELECT * FROM clients";
        return connection.prepareStatement(sql);

    }

    @Override
    public PreparedStatement saveStatement(Client entity) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement deleteStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement updateStatement(Client entity) throws SQLException {
        return null;
    }
}
