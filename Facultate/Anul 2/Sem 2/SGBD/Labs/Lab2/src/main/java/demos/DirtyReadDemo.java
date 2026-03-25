package demos;

import utils.DatabaseConnection;

import java.sql.*;

public class DirtyReadDemo {

    private static final String URL = "jdbc:h2:mem:dirtyread;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void setup() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS employees_dirty (id INT PRIMARY KEY, salary DECIMAL(10,2))");
            stmt.execute("MERGE INTO employees_dirty VALUES(1, 5000.00)");
        }
    }

    public static void run() {
        try { setup(); } catch (SQLException e) {
            System.out.println("Eroare setup: " + e.getMessage());
            return;
        }

        runWithProblem();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("UPDATE employees_dirty SET salary = 5000.00 WHERE id = 1");
        } catch (SQLException ignored) {}

        runWithSolution();

        try (Connection conn = getConnection()){
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT id, salary FROM employees_dirty WHERE id = 1");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    double salary = rs.getDouble("salary");
                    System.out.printf("[FINAL] ID: %d, Salary: %.2f%n", id, salary);
                }
        }catch(SQLException e){
            System.out.println("Eroare la afisarea starii finale: " + e.getMessage());
        }
    }

    private static void runWithProblem() {
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread transactionA = new Thread(() -> {
            try (Connection conn = getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[A] BEGIN TRANSACTION");

                PreparedStatement update = conn.prepareStatement(
                        "UPDATE employees_dirty SET salary = 10000 WHERE id = 1");
                update.executeUpdate();
                System.out.println("[A] Salariu actualizat la 10000 (ne-comis)");

                synchronized (lock1) { lock1.notifyAll(); }
                synchronized (lock2) {
                    try { lock2.wait(3000); } catch (InterruptedException ignored) {}
                }

                conn.rollback();
                System.out.println("[A] ROLLBACK efectuat! Salariul revine la 5000");

            } catch (SQLException e) {
                System.out.println("[A] Eroare: " + e.getMessage());
            }
        });

        Thread transactionB = new Thread(() -> {
            synchronized (lock1) {
                try { lock1.wait(3000); } catch (InterruptedException ignored) {}
            }

            try (Connection conn = getConnection()) {
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                conn.setAutoCommit(false);
                System.out.println("[B] BEGIN TRANSACTION (READ UNCOMMITTED)");

                PreparedStatement select = conn.prepareStatement(
                        "SELECT salary FROM employees_dirty WHERE id = 1");
                ResultSet rs = select.executeQuery();
                if (rs.next())
                    System.out.println("[B] Salariu citit: " + rs.getDouble("salary")
                            + " <-- DIRTY READ! Valoare ne-comisa!");

                conn.commit();
                System.out.println("[B] COMMIT");

            } catch (SQLException e) {
                System.out.println("[B] Eroare: " + e.getMessage());
            } finally {
                synchronized (lock2) { lock2.notifyAll(); }
            }
        });

        transactionA.start();
        transactionB.start();
        try { transactionA.join(); transactionB.join(); } catch (InterruptedException ignored) {}

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT salary FROM employees_dirty WHERE id = 1");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                System.out.println("[FINAL] Salariu in BD: " + rs.getDouble("salary")
                        + " (B a citit o valoare care nu a existat niciodata!)");
        } catch (SQLException e) {
            System.out.println("[FINAL] Eroare: " + e.getMessage());
        }
    }

    private static void runWithSolution() {
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread transactionA = new Thread(() -> {
            try (Connection conn = getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[A] BEGIN TRANSACTION");

                PreparedStatement update = conn.prepareStatement(
                        "UPDATE employees_dirty SET salary = 10000 WHERE id = 1");
                update.executeUpdate();
                System.out.println("[A] Salariu actualizat la 10000 (ne-comis)");

                synchronized (lock1) { lock1.notifyAll(); }
                synchronized (lock2) {
                    try { lock2.wait(3000); } catch (InterruptedException ignored) {}
                }

                conn.rollback();
                System.out.println("[A] ROLLBACK efectuat!");

            } catch (SQLException e) {
                System.out.println("[A] Eroare: " + e.getMessage());
            }
        });

        Thread transactionB = new Thread(() -> {
            synchronized (lock1) {
                try { lock1.wait(3000); } catch (InterruptedException ignored) {}
            }

            try (Connection conn = getConnection()) {
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                conn.setAutoCommit(false);
                System.out.println("[B] BEGIN TRANSACTION (READ COMMITTED)");

                PreparedStatement select = conn.prepareStatement(
                        "SELECT salary FROM employees_dirty WHERE id = 1");
                ResultSet rs = select.executeQuery();
                if (rs.next())
                    System.out.println("[B] Salariu citit: " + rs.getDouble("salary")
                            + " <-- Valoare corecta (5000), dirty read prevenit!");

                conn.commit();
                System.out.println("[B] COMMIT");

            } catch (SQLException e) {
                System.out.println("[B] Eroare: " + e.getMessage());
            } finally {
                synchronized (lock2) { lock2.notifyAll(); }
            }
        });

        transactionA.start();
        transactionB.start();
        try { transactionA.join(); transactionB.join(); } catch (InterruptedException ignored) {}
    }
}