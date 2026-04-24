package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private final Properties properties;
    private final Logger logger = LogManager.getLogger();
    private Connection connection = null;

    public JdbcUtils(Properties properties) {
        this.properties = properties;
    }

    private Connection getNewConnection(){

        String url=properties.getProperty("jdbc.url");
        String user=properties.getProperty("jdbc.user");
        String pass=properties.getProperty("jdbc.pass");

        Connection con=null;
        try {
            if (user!=null && pass!=null)
                con= DriverManager.getConnection(url,user,pass);
            else
                con=DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error getting connection "+e);
        }
        return con;
    }

    public Connection getConnection(){
        logger.info("Connecting to database...");
        try {
            if (connection==null || connection.isClosed())
                connection=getNewConnection();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.info("Database connection established");
        return connection;
    }

}
