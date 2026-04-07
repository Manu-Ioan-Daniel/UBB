package utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.Level;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseManager{

    private static DatabaseManager instance;
    private final HikariDataSource dataSource;

    private DatabaseManager(){

        Properties prop = new Properties();
        try{
            prop.load(DatabaseManager.class.getResourceAsStream("/sql/db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Logger hikariLogger = (Logger) LoggerFactory.getLogger("com.zaxxer.hikari");
        hikariLogger.setLevel(Level.INFO);

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(prop.getProperty("db.url"));
        config.setUsername(prop.getProperty("db.username"));
        config.setPassword(prop.getProperty("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(prop.getProperty("db.poolSize")));
        config.setConnectionTimeout(2000);

        this.dataSource = new HikariDataSource(config);
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

}