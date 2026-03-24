package demos;

import utils.DatabaseConnection;

import java.sql.*;

public class DeadlockDemo {

    public static void run() {
        try { DatabaseConnection.resetData(); } catch (SQLException e) { return; }

        runDeadlock();

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        try { DatabaseConnection.resetData(); } catch (SQLException e) { return; }
        runSolution();
    }

    private static void runDeadlock() {
        Object lockA = new Object();
        Object lockB = new Object();

        Thread transactionA = new Thread(() -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[A] Pornesc tranzactia");

                PreparedStatement update1 = conn.prepareStatement(
                        "UPDATE employees SET salary = 6000 WHERE id = 1");
                update1.executeUpdate();
                System.out.println("[A] Am blocat randul id=1");

                synchronized (lockA) { lockA.notifyAll(); }
                synchronized (lockB) {
                    try { lockB.wait(3000); } catch (InterruptedException ignored) {}
                }

                System.out.println("[A] Incerc sa blochez si randul id=2");
                PreparedStatement update2 = conn.prepareStatement(
                        "UPDATE employees SET salary = 7000 WHERE id = 2");
                update2.executeUpdate();
                conn.commit();
                System.out.println("[A] Commit facut");

            } catch (SQLException e) {
                System.out.println("[A] Deadlock detectat: " + e.getMessage());
                System.out.println("[A] Tranzactia A a fost aleasa ca victima, rollback automat");
            }
        });

        Thread transactionB = new Thread(() -> {
            synchronized (lockA) {
                try { lockA.wait(3000); } catch (InterruptedException ignored) {}
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[B] Pornesc tranzactia");

                PreparedStatement update1 = conn.prepareStatement(
                        "UPDATE employees SET salary = 6000 WHERE id = 2");
                update1.executeUpdate();
                System.out.println("[B] Am blocat randul id=2");

                synchronized (lockB) { lockB.notifyAll(); }
                Thread.sleep(500);

                System.out.println("[B] Incerc sa blochez si randul id=1");
                PreparedStatement update2 = conn.prepareStatement(
                        "UPDATE employees SET salary = 7000 WHERE id = 1");
                update2.executeUpdate();
                conn.commit();
                System.out.println("[B] Commit facut");

            } catch (SQLException e) {
                System.out.println("[B] Deadlock detectat: " + e.getMessage());
                System.out.println("[B] Tranzactia B a fost aleasa ca victima, rollback automat");
            } catch (InterruptedException ignored) {}
        });

        transactionA.start();
        transactionB.start();
        try { transactionA.join(10000); transactionB.join(10000); }
        catch (InterruptedException ignored) {}
    }

    private static void runSolution() {
        Thread transactionA = new Thread(() -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[A] Pornesc tranzactia (ordine buna: id=1 -> id=2)");

                PreparedStatement u1 = conn.prepareStatement(
                        "UPDATE employees SET salary = 6000 WHERE id = 1");
                u1.executeUpdate();
                System.out.println("[A] Am blocat si actualizat id=1");

                Thread.sleep(500);

                PreparedStatement u2 = conn.prepareStatement(
                        "UPDATE employees SET salary = 7000 WHERE id = 2");
                u2.executeUpdate();
                System.out.println("[A] Am blocat si actualizat id=2");

                conn.commit();
                System.out.println("[A] Commit facut, fara deadlock");

            } catch (SQLException | InterruptedException e) {
                System.out.println("[A] Eroare: " + e.getMessage());
            }
        });

        Thread transactionB = new Thread(() -> {
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}

            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                System.out.println("[B] Pornesc tranzactia (ordine buna: id=1 -> id=2)");

                PreparedStatement u1 = conn.prepareStatement(
                        "UPDATE employees SET salary = 8000 WHERE id = 1");
                u1.executeUpdate();
                System.out.println("[B] Am blocat si actualizat id=1 (dupa A)");

                PreparedStatement u2 = conn.prepareStatement(
                        "UPDATE employees SET salary = 9000 WHERE id = 2");
                u2.executeUpdate();
                System.out.println("[B] Am blocat si actualizat id=2");

                conn.commit();
                System.out.println("[B] Commit facut, fara deadlock");

            } catch (SQLException e) {
                System.out.println("[B] Eroare: " + e.getMessage());
            }
        });

        transactionA.start();
        transactionB.start();
        try { transactionA.join(10000); transactionB.join(10000); }
        catch (InterruptedException ignored) {}
    }
}