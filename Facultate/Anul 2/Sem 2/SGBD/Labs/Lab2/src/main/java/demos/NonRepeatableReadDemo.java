package demos;

import utils.DatabaseConnection;

import java.sql.*;

public class NonRepeatableReadDemo {

    public static void run() {
        
        try { DatabaseConnection.resetData(); } catch (SQLException e) { return; }
        
        runWithProblem();

        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        try { DatabaseConnection.resetData(); } catch (SQLException e) { return; }
        runWithSolution();
    }

    private static void runWithProblem() {
        Object lockAfterFirstRead = new Object();
        Object lockAfterBCommit = new Object();

        Thread transactionA = new Thread(() -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                conn.setAutoCommit(false);
                System.out.println("[A] Pornesc tranzactia (READ COMMITTED)");

                PreparedStatement select = conn.prepareStatement(
                        "SELECT salary FROM employees WHERE id = 1");

                ResultSet rs1 = select.executeQuery();
                if (rs1.next())
                    System.out.println("[A] Prima citire salariu: " + rs1.getDouble("salary"));

                synchronized (lockAfterFirstRead) { lockAfterFirstRead.notifyAll(); }
                synchronized (lockAfterBCommit) {
                    try { lockAfterBCommit.wait(3000); } catch (InterruptedException ignored) {}
                }

                ResultSet rs2 = select.executeQuery();
                if (rs2.next())
                    System.out.println("[A] A doua citire salariu: " + rs2.getDouble("salary")
                            + " <-- non-repeatable read, valoare diferita");

                conn.commit();
                System.out.println("[A] Commit");

            } catch (SQLException e) {
                System.out.println("[A] Eroare: " + e.getMessage());
            }
        });

        Thread transactionB = new Thread(() -> {
            synchronized (lockAfterFirstRead) {
                try { lockAfterFirstRead.wait(3000); } catch (InterruptedException ignored) {}
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[B] Pornesc tranzactia");

                PreparedStatement update = conn.prepareStatement(
                        "UPDATE employees SET salary = 12000 WHERE id = 1");
                update.executeUpdate();
                conn.commit();
                System.out.println("[B] Am actualizat salariul la 12000 si am dat commit");

            } catch (SQLException e) {
                System.out.println("[B] Eroare: " + e.getMessage());
            } finally {
                synchronized (lockAfterBCommit) { lockAfterBCommit.notifyAll(); }
            }
        });

        transactionA.start();
        transactionB.start();
        try { transactionA.join(); transactionB.join(); } catch (InterruptedException ignored) {}
    }

    private static void runWithSolution() {
        Object lockAfterFirstRead = new Object();
        Object lockAfterBCommit = new Object();

        Thread transactionA = new Thread(() -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                conn.setAutoCommit(false);
                System.out.println("[A] Pornesc tranzactia (REPEATABLE READ)");

                PreparedStatement select = conn.prepareStatement(
                        "SELECT salary FROM employees WHERE id = 1");

                ResultSet rs1 = select.executeQuery();
                if (rs1.next())
                    System.out.println("[A] Prima citire salariu: " + rs1.getDouble("salary"));

                synchronized (lockAfterFirstRead) { lockAfterFirstRead.notifyAll(); }
                synchronized (lockAfterBCommit) {
                    try { lockAfterBCommit.wait(3000); } catch (InterruptedException ignored) {}
                }

                ResultSet rs2 = select.executeQuery();
                if (rs2.next())
                    System.out.println("[A] A doua citire salariu: " + rs2.getDouble("salary")
                            + " <-- aceeasi valoare, repeatable read isi face treaba");

                conn.commit();
                System.out.println("[A] Commit");

            } catch (SQLException e) {
                System.out.println("[A] Eroare: " + e.getMessage());
            }
        });

        Thread transactionB = new Thread(() -> {
            synchronized (lockAfterFirstRead) {
                try { lockAfterFirstRead.wait(3000); } catch (InterruptedException ignored) {}
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[B] Pornesc tranzactia");

                PreparedStatement update = conn.prepareStatement(
                        "UPDATE employees SET salary = 12000 WHERE id = 1");
                update.executeUpdate();
                conn.commit();
                System.out.println("[B] Am actualizat salariul la 12000 si am dat commit");

            } catch (SQLException e) {
                System.out.println("[B] Eroare: " + e.getMessage());
            } finally {
                synchronized (lockAfterBCommit) { lockAfterBCommit.notifyAll(); }
            }
        });

        transactionA.start();
        transactionB.start();
        try { transactionA.join(); transactionB.join(); } catch (InterruptedException ignored) {}
    }
}