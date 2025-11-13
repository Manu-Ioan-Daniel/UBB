package repo;

import domain.Duck;
import domain.FlyingDuck;
import domain.SwimmingDuck;
import domain.User;
import enums.DuckType;
import domain.Flock;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class DatabaseRepoUser {
    private final String url;
    private final Properties props;
    private final DatabaseRepoCard repoCard;
    public DatabaseRepoUser(String host, int port, String dbName, String user, String password, DatabaseRepoCard repoCard) {
        this.repoCard = repoCard;
        this.url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;
        this.props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
    }

    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, props)) {
            //citire rate
            String sql = "SELECT u.userId, u.username, u.email, u.userPassword, d.duckType, d.speed, d.rezistance, d.flock_id " +
                    "FROM users u INNER JOIN ducks d ON u.userId = d.user_id";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Duck duck;
                long id = rs.getLong("userId");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("userPassword");
                String duckType = rs.getString("duckType");
                double speed = rs.getDouble("speed");
                double rezistance = rs.getDouble("rezistance");
                long flockId=rs.getLong("flock_id");
                if(Objects.equals(duckType, "FLYING")){
                    duck=new FlyingDuck(id, username, email, password, speed, rezistance);
                    result.add(duck);
                }else{
                    duck=new SwimmingDuck(id, username, email, password, speed, rezistance);
                    result.add(duck);
                }
                Flock<Duck> flock= (Flock<Duck>) repoCard.getById(flockId);
               if(flock!=null) {
                    flock.addDuck(duck);
                    duck.setFlock(flock);
                }
            }
            Statement stmtFriends = connection.createStatement();
            ResultSet rsFriends = stmtFriends.executeQuery("SELECT * FROM user_friends");
            while(rsFriends.next()){
                long userId=rsFriends.getLong("user_id");
                long friendId=rsFriends.getLong("friend_id");
                for(User u:result){
                    if(u.getId().equals(userId)){
                        u.addFriend(friendId);
                    }
                }
            }
            //citire persoane

        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
        return result;
    }
}
