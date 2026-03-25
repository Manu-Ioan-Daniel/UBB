package demos;

import utils.DatabaseConnection;

import java.sql.*;

public class BatchInsertDemo {
    private static final int INSERT_COUNT = 5000;
    private static final int RUNS = 3;

    public static void run() {

        System.out.println("Inseram " + INSERT_COUNT + " inregistrari, cu " + RUNS + " rulari pe fiecare varianta\n");

        long[] timesA = new long[RUNS];
        long[] timesB = new long[RUNS];
        long[] timesC = new long[RUNS];

        for (int i = 0; i < RUNS; i++) {
            System.out.println("--- Rulare " + (i + 1) + " ---");

            cleanTestData();
            timesA[i] = approach1AutoCommit();

            cleanTestData();
            timesB[i] = approach2BatchCommit();

            cleanTestData();
            timesC[i] = approach3SingleTransaction();

            System.out.println();
        }

        printResults(timesA, timesB, timesC);
        DatabaseConnection.showFinalState();
    }

    private static long approach1AutoCommit() {
        long start = System.currentTimeMillis();
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(true);
            String sql = "INSERT INTO employees (name, salary, department_id) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < INSERT_COUNT; i++) {
                    stmt.setString(1, "TestEmp_A_" + i);
                    stmt.setDouble(2, 3000 + (i % 2000));
                    stmt.setInt(3, (i % 5) + 1);
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("[A] A aparut o eroare: " + e.getMessage());
        }
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("[Abordare 1 - Auto-commit]      A durat: " + elapsed + " ms");
        return elapsed;
    }

    private static long approach2BatchCommit() {
        long start = System.currentTimeMillis();
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO employees (name, salary, department_id) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < INSERT_COUNT; i++) {
                    stmt.setString(1, "TestEmp_B_" + i);
                    stmt.setDouble(2, 3000 + (i % 2000));
                    stmt.setInt(3, (i % 5) + 1);
                    stmt.executeUpdate();
                    if ((i + 1) % 100 == 0) {
                        conn.commit();
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("[B] A aparut o eroare: " + e.getMessage());
        }
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("[Abordare 2 - Commit la 100]    A durat: " + elapsed + " ms");
        return elapsed;
    }

    private static long approach3SingleTransaction() {
        long start = System.currentTimeMillis();
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO employees (name, salary, department_id) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < INSERT_COUNT; i++) {
                    stmt.setString(1, "TestEmp_C_" + i);
                    stmt.setDouble(2, 3000 + (i % 2000));
                    stmt.setInt(3, (i % 5) + 1);
                    stmt.addBatch();
                    if ((i + 1) % 50 == 0) {
                        stmt.executeBatch();
                    }
                }
                stmt.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("[C] A aparut o eroare: " + e.getMessage());
        }
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("[Abordare 3 - Batch+Tranzactie] A durat: " + elapsed + " ms");
        return elapsed;
    }

    private static void cleanTestData() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM employees WHERE id > 3");
        } catch (SQLException e) {
            System.out.println("[CLEAN] A aparut o eroare: " + e.getMessage());
        }
    }

    private static void printResults(long[] a, long[] b, long[] c) {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║           REZULTATE PERFORMANTA BATCH INSERT         ║");
        System.out.println("╠══════════════╦═════════════╦═════════════╦═══════════╣");
        System.out.printf("║ %-12s ║ %-11s ║ %-11s ║ %-9s ║%n",
                "Rulare", "Auto-commit", "Commit/100", "Batch");
        System.out.println("╠══════════════╬═════════════╬═════════════╬═══════════╣");

        for (int i = 0; i < RUNS; i++) {
            System.out.printf("║ Rulare %-5d ║ %8d ms ║ %8d ms ║ %6d ms ║%n",
                    (i + 1), a[i], b[i], c[i]);
        }

        long avgA = average(a), avgB = average(b), avgC = average(c);
        System.out.println("╠══════════════╬═════════════╬═════════════╬═══════════╣");
        System.out.printf("║ %-12s ║ %8d ms ║ %8d ms ║ %6d ms ║%n",
                "MEDIE", avgA, avgB, avgC);
        System.out.println("╚══════════════╩═════════════╩═════════════╩═══════════╝");

        System.out.println("\nConcluzii:");
        System.out.printf("  Batch vs Auto-commit: de ~%.2fx mai rapid%n", (double)avgA/avgC);
        System.out.printf("  Batch vs Commit/100: de ~%.2fx mai rapid%n", (double)avgB/avgC);
    }

    private static long average(long[] arr) {
        long sum = 0;
        for (long v : arr) sum += v;
        return sum / arr.length;
    }
}