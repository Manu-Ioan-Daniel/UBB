package demos;

import utils.DatabaseConnection;

import java.sql.*;

public class LostUpdateDemo {

    public static void run() {
        try { DatabaseConnection.resetData(); } catch (SQLException e) { return; }

        runWithProblem();

        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        try { DatabaseConnection.resetData(); } catch (SQLException e) { return; }

        runWithSolution();

        DatabaseConnection.showFinalState();
    }

    private static void runWithProblem() {
        Object lockAfterARead = new Object();
        Object lockAfterBCommit = new Object();

        Thread transactionA = new Thread(() -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[A] Pornesc tranzactia");

                PreparedStatement select = conn.prepareStatement(
                        "SELECT salary FROM employees WHERE id = 1");
                ResultSet rs = select.executeQuery();
                double salary = 0;
                if (rs.next()) {
                    salary = rs.getDouble("salary");
                    System.out.println("[A] Am citit salariul: " + salary);
                }
                double newSalary = salary + 1000;

                synchronized (lockAfterARead) { lockAfterARead.notifyAll(); }
                synchronized (lockAfterBCommit) {
                    try { lockAfterBCommit.wait(3000); } catch (InterruptedException ignored) {}
                }

                PreparedStatement update = conn.prepareStatement(
                        "UPDATE employees SET salary = ? WHERE id = 1");
                update.setDouble(1, newSalary);
                update.executeUpdate();
                conn.commit();
                System.out.println("[A] Am scris salariul: " + newSalary + " si am dat commit");

            } catch (SQLException e) {
                System.out.println("[A] Eroare: " + e.getMessage());
            }
        });

        Thread transactionB = new Thread(() -> {
            synchronized (lockAfterARead) {
                try { lockAfterARead.wait(3000); } catch (InterruptedException ignored) {}
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[B] Pornesc tranzactia");

                PreparedStatement select = conn.prepareStatement(
                        "SELECT salary FROM employees WHERE id = 1");
                ResultSet rs = select.executeQuery();
                double salary = 0;
                if (rs.next()) {
                    salary = rs.getDouble("salary");
                    System.out.println("[B] Am citit salariul: " + salary);
                }
                double newSalary = salary + 500;

                PreparedStatement update = conn.prepareStatement(
                        "UPDATE employees SET salary = ? WHERE id = 1");
                update.setDouble(1, newSalary);
                update.executeUpdate();
                conn.commit();
                System.out.println("[B] Am scris salariul: " + newSalary + " si am dat commit");

            } catch (SQLException e) {
                System.out.println("[B] Eroare: " + e.getMessage());
            } finally {
                synchronized (lockAfterBCommit) { lockAfterBCommit.notifyAll(); }
            }
        });

        transactionA.start();
        transactionB.start();
        try { transactionA.join(); transactionB.join(); } catch (InterruptedException ignored) {}

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT salary FROM employees WHERE id = 1");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                System.out.println("[FINAL] Salariu in BD: " + rs.getDouble("salary"));
        } catch (SQLException e) {
            System.out.println("[FINAL] Eroare: " + e.getMessage());
        }
    }

    private static void runWithSolution() {
        Object lockAfterALock = new Object();

        Thread transactionA = new Thread(() -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[A] Pornesc tranzactia");

                PreparedStatement select = conn.prepareStatement(
                        "SELECT salary FROM employees WHERE id = 1 FOR UPDATE");
                ResultSet rs = select.executeQuery();
                double salary = 0;
                if (rs.next()) {
                    salary = rs.getDouble("salary");
                    System.out.println("[A] Am citit si blocat randul, salariu: " + salary);
                }

                synchronized (lockAfterALock) { lockAfterALock.notifyAll(); }
                Thread.sleep(2000);

                double newSalary = salary + 1000;
                PreparedStatement update = conn.prepareStatement(
                        "UPDATE employees SET salary = ? WHERE id = 1");
                update.setDouble(1, newSalary);
                update.executeUpdate();
                conn.commit();
                System.out.println("[A] Am scris salariul: " + newSalary + ", commit dat, randul s-a deblocat");

            } catch (SQLException | InterruptedException e) {
                System.out.println("[A] Eroare: " + e.getMessage());
            }
        });

        Thread transactionB = new Thread(() -> {
            synchronized (lockAfterALock) {
                try { lockAfterALock.wait(3000); } catch (InterruptedException ignored) {}
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[B] Pornesc tranzactia, astept randul blocat de A");

                PreparedStatement select = conn.prepareStatement(
                        "SELECT salary FROM employees WHERE id = 1 FOR UPDATE");
                ResultSet rs = select.executeQuery();
                double salary = 0;
                if (rs.next()) {
                    salary = rs.getDouble("salary");
                    System.out.println("[B] Randul s-a deblocat, am citit salariul actualizat: " + salary);
                }

                double newSalary = salary + 500;
                PreparedStatement update = conn.prepareStatement(
                        "UPDATE employees SET salary = ? WHERE id = 1");
                update.setDouble(1, newSalary);
                update.executeUpdate();
                conn.commit();
                System.out.println("[B] Am scris salariul: " + newSalary + " si am dat commit");

            } catch (SQLException e) {
                System.out.println("[B] Eroare: " + e.getMessage());
            }
        });

        transactionA.start();
        transactionB.start();
        try { transactionA.join(); transactionB.join(); } catch (InterruptedException ignored) {}

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT salary FROM employees WHERE id = 1");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                System.out.println("[FINAL] Salariu in BD: " + rs.getDouble("salary")
                        + " (ar trebui 6500, ambele update-uri au fost aplicate corect)");
        } catch (SQLException e) {
            System.out.println("[FINAL] Eroare: " + e.getMessage());
        }
    }
}