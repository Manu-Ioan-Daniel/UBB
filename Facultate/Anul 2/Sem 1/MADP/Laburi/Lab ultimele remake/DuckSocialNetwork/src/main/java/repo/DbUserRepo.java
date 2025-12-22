package repo;
import domain.Duck;
import domain.Person;
import domain.User;
import enums.DuckType;
import exceptions.RepoException;
import utils.database.DbConnection;
import utils.passwords.PasswordHasher;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DbUserRepo implements Repository<Long, User>{
    private Connection connection;
    public DbUserRepo() throws SQLException {
        connection = DbConnection.getInstance().getConnection();
    }

    public Optional<User> findOne(String username) {
        String sql = """
                SELECT u.*, d.type as duck_type, d.speed, d.resistance, p.name, p.surname, p.date_of_birth, p.occupation, p.empathy_level
                FROM users u
                LEFT JOIN people p ON p.id = u.id
                LEFT JOIN ducks d ON d.id = u.id
                WHERE u.username = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return Optional.of(userFromResultSet(rs));
                }
                return Optional.empty();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private User userFromResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String username = rs.getString("username");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String type = rs.getString("type");
        if(type.equals("duck")){

            DuckType duckType = DuckType.valueOf(rs.getString("duck_type"));
            Double speed = rs.getDouble("speed");
            Double resistance = rs.getDouble("resistance");
            Duck d = new Duck(username, email, password, duckType, speed, resistance);
            d.setId(id);
            return d;
        }else{
            String name = rs.getString("name");
            String surname =  rs.getString("surname");
            LocalDate date = rs.getDate("date_of_birth").toLocalDate();
            String occupation = rs.getString("occupation");
            int empathyLevel = rs.getInt("empathy_level");
            Person p = new Person(username,email,password,name,surname,date,occupation,empathyLevel);
            p.setId(id);
            return p;
        }

    }

    @Override
    public Optional<User> findOne(Long id) {
        String sql = """
                SELECT u.*, d.type as duck_type, d.speed, d.resistance, p.name, p.surname, p.date_of_birth, p.occupation, p.empathy_level
                FROM users u
                LEFT JOIN people p ON p.id = u.id
                LEFT JOIN ducks d ON d.id = u.id
                WHERE u.id = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return Optional.of(userFromResultSet(rs));
                }
                return Optional.empty();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    public Iterable<User> findUsersFromPage(int offset, int pageSize) {
        Set<User> users = new HashSet<>();
        String sql = """
                SELECT u.*, d.type as duck_type, d.speed, d.resistance, p.name, p.surname, p.date_of_birth, p.occupation, p.empathy_level
                FROM users u
                LEFT JOIN people p ON p.id = u.id
                LEFT JOIN ducks d ON d.id = u.id
                ORDER BY username
                LIMIT ?
                OFFSET ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,pageSize);
            ps.setInt(2,offset);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    users.add(userFromResultSet(rs));
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Iterable<User> findAll() {
        String sql = """
                SELECT u.*, d.type as duck_type, d.speed, d.resistance, p.name, p.surname, p.date_of_birth, p.occupation, p.empathy_level
                FROM users u
                LEFT JOIN people p ON p.id = u.id
                LEFT JOIN ducks d ON d.id = u.id
                """;
        Set<User> users = new HashSet<>();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    User user = userFromResultSet(rs);
                    users.add(user);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Optional<User> save(User entity) {
        String sql = """
                INSERT INTO users(username,password,email,type) VALUES (?,?,?,?)
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, entity.getUsername());
            byte[] salt = PasswordHasher.generateSalt();
            byte[] hashedPassword = PasswordHasher.hashPassword(entity.getPassword(), salt);
            String hashedPasswordString = PasswordHasher.byteToBase64(salt)+":"+PasswordHasher.byteToBase64(hashedPassword);
            ps.setString(2, hashedPasswordString);
            ps.setString(3, entity.getEmail());
            ps.setString(4,entity instanceof Duck ? "duck" : "person");
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            long id;
            if(rs.next()){
                id=rs.getLong(1);
            }else{
                return Optional.empty();
            }
            if(entity instanceof Duck duck){
                String sql2 = """
                    INSERT INTO ducks (id,type,speed,resistance) VALUES (?,?,?,?)
                    """;
                try(PreparedStatement ps2 = connection.prepareStatement(sql2)){
                    ps2.setLong(1, id);
                    ps2.setString(2,duck.getType().toString());
                    ps2.setDouble(3,duck.getSpeed());
                    ps2.setDouble(4,duck.getResistance());
                    ps2.executeUpdate();
                }
            }else if(entity instanceof Person person){
                String sql2 = """
                    INSERT INTO people (id,name,surname,date_of_birth,occupation,empathy_level) VALUES (?,?,?,?,?,?)
                    """;
                try(PreparedStatement ps2 = connection.prepareStatement(sql2)){
                    ps2.setLong(1, id);
                    ps2.setString(2,person.getName());
                    ps2.setString(3,person.getSurname());
                    ps2.setDate(4, Date.valueOf(person.getDateOfBirth()));
                    ps2.setString(5,person.getOccupation());
                    ps2.setInt(6,person.getEmpathyLevel());
                    ps2.executeUpdate();
                }
            }
        }catch(SQLException e){
            if(e.getSQLState().equals("23505")){
                if(e.getMessage().contains("username")){
                    throw new RepoException("Duplicate username");
                }else{
                    throw new RepoException("Duplicate email");
                }
            }
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> delete(Long id) {
        String sql = """
                DELETE
                FROM users
                WHERE id = ?
                """;
        Optional<User>  user = findOne(id);
        if(user.isEmpty()){
            return user;
        }
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, id);
            ps.executeUpdate();
            return user;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> update(User entity) {
        return Optional.empty();
    }

    public int countUsers(){
        String sql = """
                SELECT COUNT(*)
                FROM users
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            if(rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    public int countDucks(){
        String sql = """
                SELECT COUNT(*)
                FROM ducks
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            if(rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    public int countPeople(){
        String sql = """
                SELECT COUNT(*)
                FROM people
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            if(rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


}
