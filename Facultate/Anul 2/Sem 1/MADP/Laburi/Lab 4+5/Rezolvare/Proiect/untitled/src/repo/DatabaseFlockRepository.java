package repo;
import domain.Duck;
import domain.Flock;
import enums.DuckType;
import errors.RepoError;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DatabaseFlockRepository {
    private final String databaseURL;
    public DatabaseFlockRepository(String databaseURL) {
        this.databaseURL = databaseURL;
    }
    public List<Flock> getFlocks() {
        List<Flock> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "SELECT flockId, flockName FROM flocks";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("flockId");
                String flockName = rs.getString("flockName");
                List<Duck> members=getFlockMembers(id);
                Flock flock= new Flock(id, flockName, members);
                result.add(flock);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving flocks from database", e);
        }
        return result;
    }
    private List<Duck> getFlockMembers(Long flockId) {
        List<Duck> members = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL)) {

            String sql = "SELECT u.userId, u.username, u.email, u.userPassword, d.duckType, d.duckSpeed, d.duckRezistance " +
                    "FROM flockMembers fm " +
                    "INNER JOIN users u ON fm.userId = u.userId " +
                    "INNER JOIN ducks d ON u.userId = d.userId " +
                    "WHERE fm.flockId = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, flockId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("userId");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("userPassword");
                String duckType = rs.getString("duckType");
                double speed = rs.getDouble("duckSpeed");
                double rezistance = rs.getDouble("duckRezistance");

                Duck duck = new Duck(id, username, email, password, DuckType.valueOf(duckType), speed, rezistance);
                members.add(duck);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving flock members from database", e);
        }
        return members;
    }

    public void addFlock(Flock flock) {
        if(flockExists(flock.getId())) {
            throw new RepoError("Flock with ID " + flock.getId() + " already exists.");
        }
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "INSERT INTO flocks (flockId,flockName) VALUES (?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, flock.getId());
            stmt.setString(2, flock.getFlockName());
            stmt.executeUpdate();
            String sql2 = "INSERT INTO flockmembers (userId,flockId) VALUES (?,?)";
            PreparedStatement stmt2 = connection.prepareStatement(sql2);
            for(Duck duck:flock.getMembers()) {
                stmt2.setLong(1, duck.getId());
                stmt2.setLong(2, flock.getId());
                stmt2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding flock to database", e);
        }
    }
    public void addDuckToFlock(Flock flock,Duck duck){
        Long flockId=flock.getId();
        Long duckId=duck.getId();
        if(!flockExists(flockId)) {
            throw new RepoError("Flock with ID " + flockId + " does not exist.");
        }
        if(isDuckInFlock(duckId)) {
            throw new RepoError("Duck with ID " + duckId + " is already in a flock ");
        }
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "INSERT INTO flockmembers (userId,flockId) VALUES (?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, duckId);
            stmt.setLong(2, flockId);
            stmt.executeUpdate();
            flock.addDuck(duck);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding duck to flock in database", e);
        }
    }
    private boolean flockExists(Long flockId) {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "SELECT 1 FROM flocks WHERE flockId = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, flockId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking if flock exists in database: " + e.getMessage());
        }
        return false;
    }
    private boolean isDuckInFlock(Long duckId) {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "SELECT 1 FROM flockmembers WHERE userId=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, duckId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking if duck is in flock in database: " + e.getMessage());
        }
        return false;
    }
}
