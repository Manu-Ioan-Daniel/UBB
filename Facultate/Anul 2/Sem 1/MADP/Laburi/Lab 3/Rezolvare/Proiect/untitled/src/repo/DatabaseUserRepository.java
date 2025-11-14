package repo;

import domain.Duck;
import domain.Person;
import domain.User;
import enums.DuckType;
import errors.RepoError;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUserRepository {
    private final String databaseURL;
    public DatabaseUserRepository(String databaseURL) {
        this.databaseURL = databaseURL;
    }
    public List<User> getUsers() {
        List<User> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            //citire rate
            String sql = "SELECT u.userId, u.username, u.email, u.userPassword, d.duckType, d.speed, d.rezistance, d.flock_id " +
                    "FROM users u INNER JOIN ducks d ON u.userId = d.user_id";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Duck duck;
                Long id = rs.getLong("userId");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("userPassword");
                String duckType = rs.getString("duckType");
                double speed = rs.getDouble("duckSpeed");
                double rezistance = rs.getDouble("duckRezistance");
                long flockId=rs.getLong("flock_id");
                duck= new Duck(id, username, email, password, DuckType.valueOf(duckType), speed, rezistance);
                if(!rs.wasNull()){
                    duck.setFlock(flockId);
                }
                result.add(duck);

            }
            //citire persoane
            sql = "SELECT u.userId, u.username, u.email, u.userPassword, p.personName, p.personSurname, p.personOccupation, p.personDateOfBirth, p.personEmpathyScore " +
                    "FROM users u INNER JOIN persons p ON u.userId = p.user_id";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("userId");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("userPassword");
                String name = rs.getString("personName");
                String surname = rs.getString("personSurname");
                String occupation = rs.getString("personOccupation");
                String dateOfBirth = rs.getString("personDateOfBirth");
                int empathyScore = rs.getInt("personEmpathyScore");
                Person person = new Person(id, username, email, password, name, surname, occupation, dateOfBirth, empathyScore);
                result.add(person);
            }
            Statement stmtFriends = connection.createStatement();
            ResultSet rsFriends = stmtFriends.executeQuery("SELECT * FROM user_friends");
            Map<Long, User> userMap = new HashMap<>();
            for (User u : result) {
                userMap.put(u.getId(), u);
            }

            while (rsFriends.next()) {
                long userId = rsFriends.getLong("user_id");
                long friendId = rsFriends.getLong("friend_id");
                User u = userMap.get(userId);
                if (u != null) {
                    u.addFriend(friendId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
        return result;
    }
    public void addUser(User user) {
        if(userExists(user.getId())){
            throw new RepoError("User with id "+user.getId()+" already exists.");
        }
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "INSERT INTO users (userId,username, email, userPassword) VALUES (?,?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.executeUpdate();
            long userId = user.getId();
            if (user instanceof Duck duck) {
                sql = "INSERT INTO ducks (user_id, duckType, speed, rezistance, flock_id) VALUES (?, ?, ?, ?, ?)";
                stmt = connection.prepareStatement(sql);
                stmt.setLong(1, userId);
                stmt.setString(2, duck.getType().name());
                stmt.setDouble(3, duck.getSpeed());
                stmt.setDouble(4, duck.getRezistance());
                if (duck.getFlockId() != null) {
                    stmt.setLong(5, duck.getFlockId());
                } else {
                    stmt.setNull(5, Types.BIGINT);
                    }
                stmt.executeUpdate();
                } else if (user instanceof Person person) {
                    sql = "INSERT INTO persons (user_id, personName, personSurname, personOccupation, personDateOfBirth, personEmpathyScore) VALUES (?, ?, ?, ?, ?, ?)";
                    stmt = connection.prepareStatement(sql);
                    stmt.setLong(1, userId);
                    stmt.setString(2, person.getName());
                    stmt.setString(3, person.getSurname());
                    stmt.setString(4, person.getOccupation());
                    stmt.setString(5, person.getDateOfBirth());
                    stmt.setInt(6, person.getEmpathyScore());
                    stmt.executeUpdate();
                }

        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }
    public void deleteUser(Long userId) {
        if(!userExists(userId)){
            throw new RepoError("User with id "+userId+" does not exist.");
        }
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "DELETE FROM users WHERE userId = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }
    private boolean userExists(Long userId){
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "SELECT 1 FROM users WHERE userId = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
        }
        return false;
    }
    public void addFriend(Long userId, Long friendId) {
        if(friendExists(userId, friendId)){
            throw new RepoError("Friendship between user "+userId+" and friend "+friendId+" already exists.");
        }
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "INSERT INTO user_friends (user_id, friend_id) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.setLong(2, friendId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding friend: " + e.getMessage());
        }
    }
    public void removeFriend(Long userId, Long friendId) {
        if(!friendExists(userId,friendId)){
            throw new RepoError("Friendship between user "+userId+" and friend "+friendId+" does not exist.");
        }
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "DELETE FROM user_friends WHERE user_id = ? AND friend_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.setLong(2, friendId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error removing friend: " + e.getMessage());
        }
    }
    private boolean friendExists(Long userId, Long friendId) {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "SELECT 1 FROM user_friends WHERE user_id = ? AND friend_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.setLong(2, friendId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error checking friendship existence: " + e.getMessage());
        }
        return false;
    }
}

