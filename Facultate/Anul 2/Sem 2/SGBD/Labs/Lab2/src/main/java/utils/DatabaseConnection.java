package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/lab2";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void resetData() throws SQLException {
        try (Connection conn = getConnection();
             var stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM employees WHERE id > 3");
            stmt.executeUpdate("UPDATE employees SET salary = 5000.00 WHERE id = 1");
            stmt.executeUpdate("UPDATE employees SET salary = 6000.00 WHERE id = 2");
            stmt.executeUpdate("UPDATE employees SET salary = 4500.00 WHERE id = 3");
            stmt.execute("SELECT setval('employees_id_seq', 3)");

        }
    }

    public static void showFinalState() {
        try (Connection conn = getConnection()){
            var stmt = conn.createStatement();
            var rs = stmt.executeQuery("SELECT id, name, salary FROM employees ORDER BY id");
            System.out.println("Starea finala a tabelului employees:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                System.out.printf("ID: %d, Name: %s, Salary: %.2f%n", id, name, salary);
            }
        }catch(SQLException e){
            System.out.println("Eroare la afisarea starii finale: " + e.getMessage());
        }
    }
}