package repo;

import domain.User;
import event.Event;
import event.RaceEvent;

import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class DatabaseEventRepository {
    private final String url;
    private final DatabaseUserRepository userRepository;
    public DatabaseEventRepository(String url, DatabaseUserRepository userRepository) {
        this.url = url;
        this.userRepository = userRepository;
    }
    public List<Event> getAllEvents(){
        List<Event> result=new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url)){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM events");

        }catch(SQLException e){
            throw new RuntimeException("Error retrieving events from database", e);
        }
        return result;

    }

}
