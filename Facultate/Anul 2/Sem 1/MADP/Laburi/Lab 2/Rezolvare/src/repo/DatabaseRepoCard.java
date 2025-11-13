package repo;

import domain.Duck;
import domain.Flock;
import domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseRepoCard {
    private final String url;
    private final Properties props;
    public DatabaseRepoCard(String host, int port, String dbName, String user, String password) {
        this.url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;
        this.props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
    }

    public List<Flock<Duck>> getAll() {
        List<Flock<Duck>> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, props)) {
            String sql = "SELECT * FROM flocks";
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                 while (rs.next()) {
                    long id = rs.getLong("flockId");
                    String name = rs.getString("flock_name");
                    Flock<Duck> flock = new Flock<>(id, name, new ArrayList<>());
                    result.add(flock);
                 }
            }
        }

        catch (SQLException e) {
            System.err.println("Error fetching flocks: " + e.getMessage());
        }

        return result;
    }
    public Flock<Duck> getById(long id) {
        for (Flock<Duck> flock : getAll()) {
            if (flock.getId() == id) return flock;
        }
        return null;
    }
}
