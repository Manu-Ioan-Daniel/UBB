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

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseURL)) {

            String sql = "SELECT u.userId, u.username, u.email, u.userPassword, d.duckType, d.duckSpeed, d.duckRezistance " +
                    "FROM users u INNER JOIN ducks d ON u.userId = d.userId";
            PreparedStatement stmt = connection.prepareStatement(sql);
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

                result.add(duck);
            }

            // citire persons
            sql = "SELECT u.userId, u.username, u.email, u.userPassword, p.personName, p.personSurname, p.personOccupation, p.personDateOfBirth, p.personEmpathyScore " +
                    "FROM users u INNER JOIN persons p ON u.userId = p.userId";

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

            // friends
            Statement stmtFriends = connection.createStatement();
            ResultSet rsFriends = stmtFriends.executeQuery("SELECT * FROM userfriends");

            Map<Long, User> userMap = new HashMap<>();
            for (User u : result) {
                userMap.put(u.getId(), u);
            }
            while (rsFriends.next()) {
                long userId = rsFriends.getLong("userId");
                long friendId = rsFriends.getLong("friendId");
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
        if (userExists(user.getId())) {
            throw new RepoError("User with id " + user.getId() + " already exists.");
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

                // INSERT FARA FLOCKID
                String sql2 = "INSERT INTO ducks (userId, duckType, duckSpeed, duckRezistance) VALUES (?, ?, ?, ?)";

                PreparedStatement stmt2 = connection.prepareStatement(sql2);
                stmt2.setLong(1, userId);
                stmt2.setString(2, duck.getType().name());
                stmt2.setDouble(3, duck.getSpeed());
                stmt2.setDouble(4, duck.getRezistance());
                stmt2.executeUpdate();

            } else if (user instanceof Person person) {

                String sql3 = "INSERT INTO persons (userId, personName, personSurname, personOccupation, personDateOfBirth, personEmpathyScore) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt3 = connection.prepareStatement(sql3);
                stmt3.setLong(1, userId);
                stmt3.setString(2, person.getName());
                stmt3.setString(3, person.getSurname());
                stmt3.setString(4, person.getOccupation());
                stmt3.setString(5, person.getDateOfBirth());
                stmt3.setInt(6, person.getEmpathyScore());
                stmt3.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    public void deleteUser(Long userId) {
        if (!userExists(userId)) {
            throw new RepoError("User with id " + userId + " does not exist.");
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

    private boolean userExists(Long userId) {
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
        if (friendExists(userId, friendId)) {
            throw new RepoError("Friendship between user " + userId + " and friend " + friendId + " already exists.");
        }

        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "INSERT INTO userfriends (userId, friendId) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.setLong(2, friendId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding friend: " + e.getMessage());
        }
    }

    public void removeFriend(Long userId, Long friendId) {
        if (!friendExists(userId, friendId)) {
            throw new RepoError("Friendship between user " + userId + " and friend " + friendId + " does not exist.");
        }

        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "DELETE FROM userfriends WHERE userId = ? AND friendId = ?";
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
            String sql = "SELECT 1 FROM userfriends WHERE userId = ? AND friendId = ?";
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
    public User getUserById(Long userId){
        List<User> users= getAllUsers();
        for(User user:users){
            if(user.getId().equals(userId)){
                return user;
            }
        }
        return null;
    }
    public int duckCount(DuckType type){
        try{
            Connection connection = DriverManager.getConnection(databaseURL);
            String sql;
            if(type!=null) {
                sql = "SELECT COUNT(*) AS duckCount FROM ducks WHERE duckType=?";
            }
            else {
                sql = "SELECT COUNT(*) AS duckCount FROM ducks";
            }
            PreparedStatement stmt = connection.prepareStatement(sql);
            if(type!=null){
                stmt.setString(1,type.name());
            }
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt("duckCount");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Duck> getDucksPage(int pageIndex, int pageSize, DuckType type) {
        List<Duck> ducks = new ArrayList<>();
        String sql;
        if (type == null) {
            sql = "SELECT u.userId, u.username, u.email, u.userPassword, " +
                    "d.duckType, d.duckSpeed, d.duckRezistance " +
                    "FROM users u INNER JOIN ducks d ON u.userId = d.userId " +
                    "ORDER BY u.userId LIMIT ? OFFSET ?";
        } else {
            sql = "SELECT u.userId, u.username, u.email, u.userPassword, " +
                    "d.duckType, d.duckSpeed, d.duckRezistance " +
                    "FROM users u INNER JOIN ducks d ON u.userId = d.userId " +
                    "WHERE d.duckType = ? " +
                    "ORDER BY u.userId LIMIT ? OFFSET ?";
        }

        Map<Long, Duck> duckMap = new HashMap<>();

        try (Connection c = DriverManager.getConnection(databaseURL);
             PreparedStatement ps = c.prepareStatement(sql)) {

            int paramIndex = 1;
            if (type != null) {
                ps.setString(paramIndex++, type.name());
            }

            ps.setInt(paramIndex++, pageSize);
            ps.setInt(paramIndex, pageIndex * pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Duck d = new Duck(
                        rs.getLong("userId"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("userPassword"),
                        DuckType.valueOf(rs.getString("duckType")),
                        rs.getDouble("duckSpeed"),
                        rs.getDouble("duckRezistance")
                );
                ducks.add(d);
                duckMap.put(d.getId(), d);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //adaugare prieteni
        try (Connection c = DriverManager.getConnection(databaseURL);
             PreparedStatement ps = c.prepareStatement(
                     "SELECT userId, friendId FROM userfriends WHERE userId IN (" +
                             String.join(",", duckMap.keySet().stream().map(id -> "?").toList()) +
                             ")")) {

            int index = 1;
            for (Long id : duckMap.keySet()) {
                ps.setLong(index++, id);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long userId = rs.getLong("userId");
                long friendId = rs.getLong("friendId");
                Duck d = duckMap.get(userId);
                if (d != null) d.addFriend(friendId);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ducks;
    }
    public int personCount(){
        try{
            Connection connection = DriverManager.getConnection(databaseURL);
            String sql = "SELECT COUNT(*) AS personCount FROM persons";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt("personCount");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Person> getPersonsPage(int pageIndex, int pageSize) {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT u.userId, u.username, u.email, u.userPassword, " +
                "p.personName, p.personSurname, p.personOccupation, p.personDateOfBirth, p.personEmpathyScore " +
                "FROM users u INNER JOIN persons p ON u.userId = p.userId " +
                "ORDER BY u.userId LIMIT ? OFFSET ?";

        Map<Long, Person> personMap = new HashMap<>();

        try (Connection c = DriverManager.getConnection(databaseURL);
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, pageSize);
            ps.setInt(2, pageIndex * pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Person p = new Person(
                        rs.getLong("userId"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("userPassword"),
                        rs.getString("personName"),
                        rs.getString("personSurname"),
                        rs.getString("personOccupation"),
                        rs.getString("personDateOfBirth"),
                        rs.getInt("personEmpathyScore")
                );
                persons.add(p);
                personMap.put(p.getId(), p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //adaugare prieteni
        try (Connection c = DriverManager.getConnection(databaseURL);
             PreparedStatement ps = c.prepareStatement(
                     "SELECT userId, friendId FROM userfriends WHERE userId IN (" +
                             String.join(",", personMap.keySet().stream().map(id -> "?").toList()) +
                             ")")) {

            int index = 1;
            for (Long id : personMap.keySet()) {
                ps.setLong(index++, id);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long userId = rs.getLong("userId");
                long friendId = rs.getLong("friendId");
                Person p = personMap.get(userId);
                if (p != null) p.addFriend(friendId);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return persons;
    }
}
