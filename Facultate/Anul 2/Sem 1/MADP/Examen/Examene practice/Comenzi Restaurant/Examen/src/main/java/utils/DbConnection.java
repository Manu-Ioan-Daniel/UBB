package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection instance = null;
    private final Connection connection;
    private DbConnection()  throws SQLException {
        connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/Comenzi Restaurant?user=postgres&password=123");
    }
    public static DbConnection getInstance() {
        if(instance==null){
            try {
                instance = new DbConnection();
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
