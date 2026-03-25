package demos;

import utils.DatabaseConnection;

import java.sql.*;

public class PhantomReadDemo {

    public static void run() {

        try { DatabaseConnection.resetData(); } catch (SQLException e) { return; }

        runWithProblem();

        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}


        try { DatabaseConnection.resetData(); } catch (SQLException e) { return; }

        runWithSolution();
    }

    private static void runWithProblem() {
        Object lockAfterFirstCount = new Object();
        Object lockAfterBInsert = new Object();

        Thread transactionA = new Thread(() -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                conn.setAutoCommit(false);
                System.out.println("[A] Pornesc tranzactia");

                PreparedStatement count = conn.prepareStatement(
                        "SELECT COUNT(*) AS cnt FROM employees WHERE department_id = 5");

                ResultSet rs1 = count.executeQuery();
                if (rs1.next())
                    System.out.println("[A] Prima numarare: " + rs1.getInt("cnt") + " angajati in dept 5");

                synchronized (lockAfterFirstCount) { lockAfterFirstCount.notifyAll(); }
                synchronized (lockAfterBInsert) {
                    try { lockAfterBInsert.wait(3000); } catch (InterruptedException ignored) {}
                }

                ResultSet rs2 = count.executeQuery();
                if (rs2.next())
                    System.out.println("[A] A doua numarare: " + rs2.getInt("cnt")
                            + " angajati in dept 5 <-- phantom read");

                conn.commit();
                System.out.println("[A] Commit");

            } catch (SQLException e) {
                System.out.println("[A] Eroare: " + e.getMessage());
            }
        });

        Thread transactionB = new Thread(() -> {
            synchronized (lockAfterFirstCount) {
                try { lockAfterFirstCount.wait(3000); } catch (InterruptedException ignored) {}
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[B] Pornesc tranzactia");

                PreparedStatement insert = conn.prepareStatement(
                        "INSERT INTO employees (name, salary, department_id) VALUES (?, ?, ?)");
                insert.setString(1, "Angajat Nou");
                insert.setDouble(2, 3000.00);
                insert.setInt(3, 5);
                insert.executeUpdate();
                conn.commit();
                System.out.println("[B] Am inserat un angajat nou in dept 5 si am dat commit");

            } catch (SQLException e) {
                System.out.println("[B] Eroare: " + e.getMessage());
            } finally {
                synchronized (lockAfterBInsert) { lockAfterBInsert.notifyAll(); }
            }
        });

        transactionA.start();
        transactionB.start();
        try { transactionA.join(); transactionB.join(); } catch (InterruptedException ignored) {}
    }

    private static void runWithSolution() {
        Object lockAfterFirstCount = new Object();
        Object lockAfterBInsert = new Object();

        Thread transactionA = new Thread(() -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                conn.setAutoCommit(false);
                System.out.println("[A] Pornesc tranzactia (SERIALIZABLE)");

                PreparedStatement count = conn.prepareStatement(
                        "SELECT COUNT(*) AS cnt FROM employees WHERE department_id = 5");

                ResultSet rs1 = count.executeQuery();
                if (rs1.next())
                    System.out.println("[A] Prima numarare: " + rs1.getInt("cnt") + " angajati in dept 5");

                synchronized (lockAfterFirstCount) { lockAfterFirstCount.notifyAll(); }
                synchronized (lockAfterBInsert) {
                    try { lockAfterBInsert.wait(5000); } catch (InterruptedException ignored) {}
                }

                ResultSet rs2 = count.executeQuery();
                if (rs2.next())
                    System.out.println("[A] A doua numarare: " + rs2.getInt("cnt")
                            + " angajati in dept 5 <-- aceeasi valoare, SERIALIZABLE isi face treaba");

                conn.commit();
                System.out.println("[A] Commit");

            } catch (SQLException e) {
                System.out.println("[A] Eroare: " + e.getMessage());
            }
        });

        Thread transactionB = new Thread(() -> {
            synchronized (lockAfterFirstCount) {
                try { lockAfterFirstCount.wait(3000); } catch (InterruptedException ignored) {}
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[B] Pornesc tranzactia, incerc INSERT");

                PreparedStatement insert = conn.prepareStatement(
                        "INSERT INTO employees (name, salary, department_id) VALUES (?, ?, ?)");
                insert.setString(1, "Angajat Nou Serializable");
                insert.setDouble(2, 3000.00);
                insert.setInt(3, 5);
                insert.executeUpdate();
                conn.commit();
                System.out.println("[B] INSERT facut dupa ce A a terminat");

            } catch (SQLException e) {
                System.out.println("[B] Blocat/eroare (normal cu SERIALIZABLE): " + e.getMessage());
            } finally {
                synchronized (lockAfterBInsert) { lockAfterBInsert.notifyAll(); }
            }
        });

        transactionA.start();
        transactionB.start();
        try { transactionA.join(); transactionB.join(); } catch (InterruptedException ignored) {}
    }
}