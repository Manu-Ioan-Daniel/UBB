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

    private final String databaseURL;

    public DatabaseUserRepository(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    // ------------------ Users ------------------

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseURL)) {


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
                if (u != null) u.addFriend(friendId);
            }

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

        try (Connection connection = DriverManager.getConnection(databaseURL)) {

            // Insert into users table
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

            // Insert into specific table
            if (user instanceof Duck duck) {
                String sqlDuck = "INSERT INTO ducks (userId, duckType, duckSpeed, duckRezistance) VALUES (?, ?, ?, ?)";
                PreparedStatement stmtDuck = connection.prepareStatement(sqlDuck);
                stmtDuck.setLong(1, userId);
                stmtDuck.setString(2, duck.getType().name());
                stmtDuck.setDouble(3, duck.getSpeed());
                stmtDuck.setDouble(4, duck.getRezistance());
                stmtDuck.executeUpdate();
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
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(String username) {
        if (!usernameExists(username)) {
            throw new RepoError("User with username " + username + " does not exist.");
        }
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "DELETE FROM users WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean usernameExists(String username) {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "SELECT 1 FROM users WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean emailExists(String email) {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "SELECT 1 FROM users WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------ Friends ------------------

    public void addFriend(String username, String friendUsername) {
        if (friendExists(username, friendUsername)) {
            throw new RepoError("Friendship already exists between " + username + " and " + friendUsername);
        }
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "INSERT INTO userfriends (userId, friendId) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, getUserId(username));
            stmt.setLong(2, getUserId(friendUsername));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFriend(String username, String friendUsername) {
        if (!friendExists(username, friendUsername)) {
            throw new RepoError("Friendship does not exist between " + username + " and " + friendUsername);
        }
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "DELETE FROM userfriends WHERE userId = ? AND friendId = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, getUserId(username));
            stmt.setLong(2, getUserId(friendUsername));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean friendExists(String username1, String username2) {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = """
                    SELECT 1 FROM userfriends uf
                    JOIN users u1 ON uf.userId = u1.userId
                    JOIN users u2 ON uf.friendId = u2.userId
                    WHERE u1.username = ? AND u2.username = ?
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username1);
            stmt.setString(2, username2);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long getUserId(String username) {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "SELECT userId FROM users WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getLong("userId") : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------ Duck Pagination Functions ------------------

    public int duckCount(DuckType type) {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = (type != null)
                    ? "SELECT COUNT(*) AS duckCount FROM ducks WHERE duckType=?"
                    : "SELECT COUNT(*) AS duckCount FROM ducks";
            PreparedStatement stmt = connection.prepareStatement(sql);
            if (type != null) stmt.setString(1, type.name());
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("duckCount") : 0;
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

        Map<Long, Duck> duckMap = new HashMap<>();

        try (Connection c = DriverManager.getConnection(databaseURL);
             PreparedStatement ps = c.prepareStatement(sql)) {

            int paramIndex = 1;
            if (type != null) ps.setString(paramIndex++, type.name());
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

        // Add friends
        if (!duckMap.isEmpty()) {
            addFriendsToUsers(duckMap);
        }

        return ducks;
    }

    // ------------------ Person Pagination Functions ------------------

    public int personCount() {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "SELECT COUNT(*) AS personCount FROM persons";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("personCount") : 0;
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

        if (!personMap.isEmpty()) {
            addFriendsToUsers(personMap);
        }

        return persons;
    }



    private <T extends User> void addFriendsToUsers(Map<Long, T> usersMap) {
        try (Connection c = DriverManager.getConnection(databaseURL)) {
            if (usersMap.isEmpty()) return;

            String sql = "SELECT userId, friendId FROM userfriends WHERE userId IN (" +
                    String.join(",", usersMap.keySet().stream().map(id -> "?").toList()) +
                    ")";
            PreparedStatement ps = c.prepareStatement(sql);

            int index = 1;
            for (Long id : usersMap.keySet()) {
                ps.setLong(index++, id);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long userId = rs.getLong("userId");
                long friendId = rs.getLong("friendId");
                T user = usersMap.get(userId);
                if (user != null) user.addFriend(friendId);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserById(Long uid) {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {

            String sql = """
                    SELECT u.userId, u.username, u.email, u.userPassword,
                           d.duckType, d.duckSpeed, d.duckRezistance,
                           p.personName, p.personSurname, p.personOccupation, 
                           p.personDateOfBirth, p.personEmpathyScore
                    FROM users u
                    LEFT JOIN ducks d ON u.userId = d.userId
                    LEFT JOIN persons p ON u.userId = p.userId
                    WHERE u.userId = ?
                    """;

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, uid);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("userId");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("userPassword");

                if (rs.getString("duckType") != null) {
                    return new Duck(id, username, email, password,
                            DuckType.valueOf(rs.getString("duckType")),
                            rs.getDouble("duckSpeed"),
                            rs.getDouble("duckRezistance"));
                } else if (rs.getString("personName") != null) {
                    return new Person(id, username, email, password,
                            rs.getString("personName"),
                            rs.getString("personSurname"),
                            rs.getString("personOccupation"),
                            rs.getString("personDateOfBirth"),
                            rs.getInt("personEmpathyScore"));
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByUsername(String username) {
        try (Connection connection = DriverManager.getConnection(databaseURL)) {

            String sql = """
                    SELECT u.userId, u.username, u.email, u.userPassword,
                           d.duckType, d.duckSpeed, d.duckRezistance,
                           p.personName, p.personSurname, p.personOccupation, 
                           p.personDateOfBirth, p.personEmpathyScore
                    FROM users u
                    LEFT JOIN ducks d ON u.userId = d.userId
                    LEFT JOIN persons p ON u.userId = p.userId
                    WHERE u.username = ?
                    """;

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("userId");
                String email = rs.getString("email");
                String password = rs.getString("userPassword");

                if (rs.getString("duckType") != null) {
                    return new Duck(id, username, email, password,
                            DuckType.valueOf(rs.getString("duckType")),
                            rs.getDouble("duckSpeed"),
                            rs.getDouble("duckRezistance"));
                } else if (rs.getString("personName") != null) {
                    return new Person(id, username, email, password,
                            rs.getString("personName"),
                            rs.getString("personSurname"),
                            rs.getString("personOccupation"),
                            rs.getString("personDateOfBirth"),
                            rs.getInt("personEmpathyScore"));
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
