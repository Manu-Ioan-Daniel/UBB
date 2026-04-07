package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OldDBManager {
    private static OldDBManager instance = null;
    private final Connection connection;
    private OldDBManager() throws SQLException {
        connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/sgbd_lab?user=postgres&password=123");
    }

    public static OldDBManager getInstance() {
        if(instance==null){
            try {
                instance = new OldDBManager();
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