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
        }

        String url = System.getenv().getOrDefault("DB_URL", prop.getProperty("db.url"));
        String user = System.getenv().getOrDefault("DB_USER", prop.getProperty("db.username"));
        String pass = System.getenv().getOrDefault("DB_PASS", prop.getProperty("db.password"));
        String poolSizeStr = System.getenv().getOrDefault("DB_POOL_SIZE", prop.getProperty("db.poolSize", "10"));

        Logger hikariLogger = (Logger) LoggerFactory.getLogger("com.zaxxer.hikari");
        hikariLogger.setLevel(Level.INFO);

        HikariConfig config = new HikariConfig();
        if (url != null) config.setJdbcUrl(url);
        if (user != null) config.setUsername(user);
        if (pass != null) config.setPassword(pass);
        int poolSize = 10;
        try { poolSize = Integer.parseInt(poolSizeStr); } catch (NumberFormatException ignored) {}
        config.setMaximumPoolSize(poolSize);
        config.setMinimumIdle(Math.max(1, poolSize/2));
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setPoolName("Lab4HikariPool");

        // Prepared statement cache (useful for MySQL/Hikari combos; harmless for others)
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        // Connection test query (safe default)
        config.setConnectionTestQuery("SELECT 1");

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