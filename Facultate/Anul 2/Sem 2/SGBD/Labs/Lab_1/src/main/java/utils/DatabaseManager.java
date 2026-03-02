package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static DatabaseManager instance = null;
    private final Connection connection;
    private DatabaseManager() throws SQLException {
        connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/sgbd_lab?user=postgres&password=123");
    }
    public static DatabaseManager getInstance() {
        if(instance==null){
            try {
                instance = new DatabaseManager();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
    public Connection getConnection(){
        return connection;
    }
}