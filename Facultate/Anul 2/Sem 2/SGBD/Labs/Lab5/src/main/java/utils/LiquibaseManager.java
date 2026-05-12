package utils;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class LiquibaseManager {
    private static final Logger logger = LoggerFactory.getLogger(LiquibaseManager.class);
    private static final String DB_PROPERTIES_FILE = "sql/db.properties";
    private static final String LIQUIBASE_PROPERTIES_FILE = "liquibase.properties";


    public static void initiateMigrations() {
        try {
            Properties dbProperties = loadProperties(DB_PROPERTIES_FILE);
            String url = dbProperties.getProperty("db.url");
            String username = dbProperties.getProperty("db.username");
            String password = dbProperties.getProperty("db.password");

            logger.info("Initiating Liquibase migrations for database: {}", url);

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Database database = DatabaseFactory.getInstance()
                        .findCorrectDatabaseImplementation(new JdbcConnection(connection));

                Liquibase liquibase = new Liquibase(
                        "db/changelog/db.changelog-master.xml",
                        new ClassLoaderResourceAccessor(),
                        database);

                liquibase.update("");
                logger.info("Liquibase migrations completed successfully!");

            } catch (Exception e) {
                logger.error("Error executing Liquibase migrations", e);
                throw new RuntimeException("Failed to execute Liquibase migrations", e);
            }
        } catch (IOException e) {
            logger.error("Error loading properties file", e);
            throw new RuntimeException("Failed to load database properties", e);
        }
    }


    private static Properties loadProperties(String propertiesFile) throws IOException {
        Properties properties = new Properties();
        try (var input = LiquibaseManager.class.getClassLoader()
                .getResourceAsStream(propertiesFile)) {
            if (input == null) {
                throw new IOException("Properties file not found: " + propertiesFile);
            }
            properties.load(input);
        }
        return properties;
    }
}

