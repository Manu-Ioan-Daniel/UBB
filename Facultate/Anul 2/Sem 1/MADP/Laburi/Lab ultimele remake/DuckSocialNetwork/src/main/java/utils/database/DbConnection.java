package utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection instance = null;
    private final Connection connection;
    private DbConnection() throws SQLException {
        connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/MAP2?user=postgres&password=123");
    }
    public static DbConnection getInstance() throws SQLException {
        if(instance==null){
            instance=new DbConnection();
        }
        return instance;
    }
    public Connection getConnection(){
        return connection;
    }
}
