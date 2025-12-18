package repo;

import domain.Duck;
import domain.Person;
import domain.User;
import enums.DuckType;
import errors.RepoError;
import utils.PasswordUtils;

import java.sql.*;
import java.util.*;

public class DatabaseUserRepository {

    private final Connection connection;

    public DatabaseUserRepository(String databaseURL) {
        try {
            this.connection = DriverManager.getConnection(databaseURL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------ Users ------------------

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            // Fetch Ducks
            String sqlDucks = """
                    SELECT u.userId, u.username, u.email, u.userPassword, 
                           d.duckType, d.duckSpeed, d.duckRezistance
                    FROM users u 
                    INNER JOIN ducks d ON u.userId = d.userId
                    """;
            PreparedStatement stmtDucks = connection.prepareStatement(sqlDucks);
            ResultSet rsDucks = stmtDucks.executeQuery();
            while (rsDucks.next()) {
                Duck duck = new Duck(
                        rsDucks.getLong("userId"),
                        rsDucks.getString("username"),
                        rsDucks.getString("email"),
                        rsDucks.getString("userPassword"),
                        DuckType.valueOf(rsDucks.getString("duckType")),
                        rsDucks.getDouble("duckSpeed"),
                        rsDucks.getDouble("duckRezistance")
                );
                result.add(duck);
            }
            rsDucks.close();
            stmtDucks.close();

            // Fetch Persons
            String sqlPersons = """
                    SELECT u.userId, u.username, u.email, u.userPassword, 
                           p.personName, p.personSurname, p.personOccupation, 
                           p.personDateOfBirth, p.personEmpathyScore
                    FROM users u
                    INNER JOIN persons p ON u.userId = p.userId
                    """;
            PreparedStatement stmtPersons = connection.prepareStatement(sqlPersons);
            ResultSet rsPersons = stmtPersons.executeQuery();
            while (rsPersons.next()) {
                Person person = new Person(
                        rsPersons.getLong("userId"),
                        rsPersons.getString("username"),
                        rsPersons.getString("email"),
                        rsPersons.getString("userPassword"),
                        rsPersons.getString("personName"),
                        rsPersons.getString("personSurname"),
                        rsPersons.getString("personOccupation"),
                        rsPersons.getString("personDateOfBirth"),
                        rsPersons.getInt("personEmpathyScore")
                );
                result.add(person);
            }
            rsPersons.close();
            stmtPersons.close();

            // Fetch Friends
            Statement stmtFriends = connection.createStatement();
            ResultSet rsFriends = stmtFriends.executeQuery("SELECT * FROM userfriends");

            Map<Long, User> userMap = new HashMap<>();
            for (User u : result) userMap.put(u.getId(), u);

            while (rsFriends.next()) {
                long userId = rsFriends.getLong("userId");
                long friendId = rsFriends.getLong("friendId");
                User u = userMap.get(userId);
                if (u != null) u.addFriend(friendId);
            }

            rsFriends.close();
            stmtFriends.close();

        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }

        return result;
    }

    public void addUser(User user) {
        if (usernameExists(user.getUsername())) {
            throw new RepoError("User with username: " + user.getUsername() + " already exists.");
        }
        if (emailExists(user.getEmail())) {
            throw new RepoError("User with email: " + user.getEmail() + " already exists.");
        }

        try {
            // Insert user
            String sqlUser = "INSERT INTO users (username, email, userPassword) VALUES (?, ?, ?)";
            PreparedStatement stmtUser = connection.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            stmtUser.setString(1, user.getUsername());
            stmtUser.setString(2, user.getEmail());
            stmtUser.setString(3, PasswordUtils.hashPassword(user.getPassword()));
            stmtUser.executeUpdate();

            ResultSet generatedKeys = stmtUser.getGeneratedKeys();
            long userId;
            if (generatedKeys.next()) {
                userId = generatedKeys.getLong(1);
            } else {
                throw new SQLException("No ID generated for user");
            }
            generatedKeys.close();
            stmtUser.close();

            // Insert specific table
            if (user instanceof Duck duck) {
                String sqlDuck = "INSERT INTO ducks (userId, duckType, duckSpeed, duckRezistance) VALUES (?, ?, ?, ?)";
                PreparedStatement stmtDuck = connection.prepareStatement(sqlDuck);
                stmtDuck.setLong(1, userId);
                stmtDuck.setString(2, duck.getType().name());
                stmtDuck.setDouble(3, duck.getSpeed());
                stmtDuck.setDouble(4, duck.getRezistance());
                stmtDuck.executeUpdate();
                stmtDuck.close();
            } else if (user instanceof Person person) {
                String sqlPerson = """
                        INSERT INTO persons (userId, personName, personSurname, personOccupation, 
                                             personDateOfBirth, personEmpathyScore) 
                        VALUES (?, ?, ?, ?, ?, ?)
                        """;
                PreparedStatement stmtPerson = connection.prepareStatement(sqlPerson);
                stmtPerson.setLong(1, userId);
                stmtPerson.setString(2, person.getName());
                stmtPerson.setString(3, person.getSurname());
                stmtPerson.setString(4, person.getOccupation());
                stmtPerson.setString(5, person.getDateOfBirth());
                stmtPerson.setInt(6, person.getEmpathyScore());
                stmtPerson.executeUpdate();
                stmtPerson.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(String username) {
        if (!usernameExists(username)) {
            throw new RepoError("User with username " + username + " does not exist.");
        }
        try {
            String sql = "DELETE FROM users WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean usernameExists(String username) {
        try {
            String sql = "SELECT 1 FROM users WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            rs.close();
            stmt.close();
            return exists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean emailExists(String email) {
        try {
            String sql = "SELECT 1 FROM users WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            rs.close();
            stmt.close();
            return exists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------ Friends ------------------

    public void addFriend(String username, String friendUsername) {
        if (friendExists(username, friendUsername)) {
            throw new RepoError("Friendship already exists between " + username + " and " + friendUsername);
        }
        try {
            String sql = "INSERT INTO userfriends (userId, friendId) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, getUserId(username));
            stmt.setLong(2, getUserId(friendUsername));
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFriend(String username, String friendUsername) {
        if (!friendExists(username, friendUsername)) {
            throw new RepoError("Friendship does not exist between " + username + " and " + friendUsername);
        }
        try {
            String sql = "DELETE FROM userfriends WHERE userId = ? AND friendId = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, getUserId(username));
            stmt.setLong(2, getUserId(friendUsername));
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean friendExists(String username1, String username2) {
        try {
            String sql = """
                    SELECT 1 FROM userfriends uf
                    JOIN users u1 ON uf.userId = u1.userId
                    JOIN users u2 ON uf.friendId = u2.userId
                    WHERE u1.username = ? AND u2.username = ?
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username1);
            stmt.setString(2, username2);
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            rs.close();
            stmt.close();
            return exists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long getUserId(String username) {
        try {
            String sql = "SELECT userId FROM users WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            Long id = rs.next() ? rs.getLong("userId") : null;
            rs.close();
            stmt.close();
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------ Duck Pagination ------------------

    public int duckCount(DuckType type) {
        try {
            String sql = (type != null)
                    ? "SELECT COUNT(*) AS duckCount FROM ducks WHERE duckType=?"
                    : "SELECT COUNT(*) AS duckCount FROM ducks";
            PreparedStatement stmt = connection.prepareStatement(sql);
            if (type != null) stmt.setString(1, type.name());
            ResultSet rs = stmt.executeQuery();
            int count = rs.next() ? rs.getInt("duckCount") : 0;
            rs.close();
            stmt.close();
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Duck> getDucksPage(int pageIndex, int pageSize, DuckType type) {
        List<Duck> ducks = new ArrayList<>();
        String sql = (type == null) ? """
                SELECT u.userId, u.username, u.email, u.userPassword, 
                       d.duckType, d.duckSpeed, d.duckRezistance
                FROM users u
                INNER JOIN ducks d ON u.userId = d.userId
                ORDER BY u.userId
                LIMIT ? OFFSET ?
                """ : """
                SELECT u.userId, u.username, u.email, u.userPassword, 
                       d.duckType, d.duckSpeed, d.duckRezistance
                FROM users u
                INNER JOIN ducks d ON u.userId = d.userId
                WHERE d.duckType = ?
                ORDER BY u.userId
                LIMIT ? OFFSET ?
                """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int paramIndex = 1;
            if (type != null) ps.setString(paramIndex++, type.name());
            ps.setInt(paramIndex++, pageSize);
            ps.setInt(paramIndex, pageIndex * pageSize);

            ResultSet rs = ps.executeQuery();
            Map<Long, Duck> duckMap = new HashMap<>();
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
            rs.close();
            ps.close();

            if (!duckMap.isEmpty()) addFriendsToUsers(duckMap);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ducks;
    }

    // ------------------ Person Pagination ------------------

    public int personCount() {
        try {
            String sql = "SELECT COUNT(*) AS personCount FROM persons";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int count = rs.next() ? rs.getInt("personCount") : 0;
            rs.close();
            stmt.close();
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> getPersonsPage(int pageIndex, int pageSize) {
        List<Person> persons = new ArrayList<>();
        String sql = """
                SELECT u.userId, u.username, u.email, u.userPassword, 
                       p.personName, p.personSurname, p.personOccupation, 
                       p.personDateOfBirth, p.personEmpathyScore
                FROM users u
                INNER JOIN persons p ON u.userId = p.userId
                ORDER BY u.userId
                LIMIT ? OFFSET ?
                """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pageSize);
            ps.setInt(2, pageIndex * pageSize);
            ResultSet rs = ps.executeQuery();
            Map<Long, Person> personMap = new HashMap<>();

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
            rs.close();
            ps.close();

            if (!personMap.isEmpty()) addFriendsToUsers(personMap);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return persons;
    }

    // ------------------ Helpers ------------------

    private <T extends User> void addFriendsToUsers(Map<Long, T> usersMap) {
        if (usersMap.isEmpty()) return;
        try {
            String sql = "SELECT userId, friendId FROM userfriends WHERE userId IN (" +
                    String.join(",", usersMap.keySet().stream().map(id -> "?").toList()) +
                    ")";
            PreparedStatement ps = connection.prepareStatement(sql);
            int index = 1;
            for (Long id : usersMap.keySet()) ps.setLong(index++, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long userId = rs.getLong("userId");
                long friendId = rs.getLong("friendId");
                T user = usersMap.get(userId);
                if (user != null) user.addFriend(friendId);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUserByField(String fieldName, Object value) {
        if (!List.of("userId", "username", "email", "userPassword").contains(fieldName)) {
            throw new IllegalArgumentException("Unsupported field name");
        }

        String sql = """
        SELECT u.userId, u.username, u.email, u.userPassword,
               d.duckType, d.duckSpeed, d.duckRezistance,
               p.personName, p.personSurname, p.personOccupation, 
               p.personDateOfBirth, p.personEmpathyScore
        FROM users u
        LEFT JOIN ducks d ON u.userId = d.userId
        LEFT JOIN persons p ON u.userId = p.userId
        WHERE u.""" + fieldName + " = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (value instanceof String s) stmt.setString(1, s);
            else if (value instanceof Long l) stmt.setLong(1, l);
            else throw new IllegalArgumentException("Unsupported value type");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (rs.getString("duckType") != null) {
                        return new Duck(
                                rs.getLong("userId"),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getString("userPassword"),
                                DuckType.valueOf(rs.getString("duckType")),
                                rs.getDouble("duckSpeed"),
                                rs.getDouble("duckRezistance")
                        );
                    } else if (rs.getString("personName") != null) {
                        return new Person(
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
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    public User getUserById(Long id) {
        return getUserByField("userId", id);
    }

    public User getUserByUsername(String username) {
        if(!usernameExists(username)){
            throw new RepoError("Username does not exist!");
        }
        return getUserByField("username", username);
    }

}
