package teste;

import utils.DatabaseManager;
import utils.OldDBManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerformanceTests{

    public static void run() {
        run100Connections();
        runNoCloseConnections();
        runCloseConnections();
    }

    private static void run100Connections() {

        List<Long> connectionTimes = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();
            try (Connection connection = OldDBManager.getInstance().getConnection()) {

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            long end = System.currentTimeMillis();
            connectionTimes.add(end - start);
        }

        long totalTime = connectionTimes.stream().reduce(0L, Long::sum);
        System.out.println("Average connection time(no pool): " + ((double)totalTime / connectionTimes.size()) + " ms");
        System.out.println("Total connection time(no pool): " + (totalTime));

        connectionTimes.clear();
        for (int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();
            try (Connection connection = DatabaseManager.getInstance().getConnection()) {

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            long end = System.currentTimeMillis();
            connectionTimes.add(end - start);
        }

        totalTime = connectionTimes.stream().reduce(0L, Long::sum);
        System.out.println("Average connection time(pool): " + ((double)totalTime / connectionTimes.size()) + " ms");
        System.out.println("Total connection time(pool): " + (totalTime));
    }

    private static void runNoCloseConnections() {
        List<Connection> leakedConnections = new ArrayList<>();
        int poolSize = DatabaseManager.getInstance().getDataSource().getMaximumPoolSize();
        int demoAttempts = poolSize + 5;

        try {
            for (int i = 0; i < demoAttempts; i++) {
                Connection connection = DatabaseManager.getInstance().getConnection();
                leakedConnections.add(connection);
                System.out.println("Connection " + (i + 1) + " opened (not closed)");
            }
        } catch (SQLException e) {
            System.out.println("Expected pool exhaustion after leak demo: " + e.getMessage());
        } finally {
            for (Connection connection : leakedConnections) {
                try {
                    connection.close();
                } catch (SQLException ignored) {
                }
            }
            System.out.println("Leak demo cleanup complete. Closed " + leakedConnections.size() + " connections.");
        }
    }

    private static void runCloseConnections() {

        for(int i = 0; i < 100; i++) {
            try (Connection connection = DatabaseManager.getInstance().getConnection()) {
                System.out.println("Connection " + (i + 1) + " opened");
            } catch (SQLException e) {
                System.out.println("Failed to open connection " + (i + 1) + ": " + e.getMessage());
            }
        }

    }


}
