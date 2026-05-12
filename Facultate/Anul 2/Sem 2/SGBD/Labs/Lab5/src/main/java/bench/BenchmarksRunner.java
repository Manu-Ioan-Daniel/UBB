package bench;

import teste.NPlusOneDemo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseManager;

public class BenchmarksRunner {

    public static void main(String[] args) {
        ensureReportDirs();
        System.out.println("Running N+1 demo benchmark...");
        NPlusOneDemo.runDemo();

        try {
            long benchmarkMaterieId = ProfessorDataGenerator.getAnyMaterieId();
            runProfessorBenchmarks(benchmarkMaterieId);
            runBulkBenchmarks(benchmarkMaterieId);
            runPreparedStatementReuseTest();
            CacheDemo.runCacheTest();
        } catch (Exception e) {
            System.err.println("Benchmark failed: " + e.getMessage());
            e.printStackTrace(System.err);
        }

        try (FileWriter fw = new FileWriter("reports/nplus1_summary.txt", false)) {
            fw.write("N+1 demo executed. Check console logs for detailed SQL output and Hibernate statistics.\n");
        } catch (IOException e) {
            System.err.println("Failed to write summary report: " + e.getMessage());
        }
        System.out.println("Benchmark finished. Reports written to reports/");
    }

    private static void ensureReportDirs() {
        File reports = new File("reports");
        File explain = new File(reports, "explain");
        if (!reports.exists() && !reports.mkdirs()) {
            throw new IllegalStateException("Could not create reports directory");
        }
        if (!explain.exists() && !explain.mkdirs()) {
            throw new IllegalStateException("Could not create reports/explain directory");
        }
    }

    private static void runProfessorBenchmarks(long materieId) throws Exception {
        System.out.println("Generating sample professor data (10k)...");
        ProfessorDataGenerator.generate(10000);
        Thread.sleep(1000);

        List<Long> timesEmail = new ArrayList<>();
        List<Long> timesMaterie = new ArrayList<>();
        List<Long> timesAgeRange = new ArrayList<>();
        List<Long> timesMulti = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            long t0 = System.currentTimeMillis();
            queryByEmail("prof500@example.com");
            timesEmail.add(System.currentTimeMillis() - t0);

            long t1 = System.currentTimeMillis();
            queryByMaterie(materieId);
            timesMaterie.add(System.currentTimeMillis() - t1);

            long t2 = System.currentTimeMillis();
            queryByAgeRange(30, 50);
            timesAgeRange.add(System.currentTimeMillis() - t2);

            long t3 = System.currentTimeMillis();
            queryMulti(materieId, 40);
            timesMulti.add(System.currentTimeMillis() - t3);
        }

        writeCsv("reports/professor_query_bench.csv", timesEmail, timesMaterie, timesAgeRange, timesMulti);

        ExplainCollector.explainToFile("SELECT * FROM profesori WHERE email = 'prof500@example.com'", "reports/explain/prof_email.txt");
        ExplainCollector.explainToFile("SELECT * FROM profesori WHERE materie_id = " + materieId, "reports/explain/prof_materie.txt");
    }

    private static void runBulkBenchmarks(long materieId) throws Exception {
        System.out.println("Running bulk ops benchmarks...");
        List<Long> ind = new ArrayList<>();
        List<Long> bulk = new ArrayList<>();
        List<Long> batched = new ArrayList<>();

        int[] sizes = new int[]{100, 1000};
        for (int s : sizes) {
            long t1 = BulkOperations.individualUpdates((int) materieId, s);
            ind.add(t1);
            long t2 = BulkOperations.bulkSqlUpdate((int) materieId);
            bulk.add(t2);
            long t3 = BulkOperations.batchedUpdates((int) materieId, 50, s);
            batched.add(t3);
        }

        try (FileWriter fw = new FileWriter("reports/bulk_ops.csv", false)) {
            fw.write("size,individual_ms,bulk_ms,batched_ms\n");
            for (int i = 0; i < sizes.length; i++) {
                fw.write(String.format("%d,%d,%d,%d\n", sizes[i], ind.get(i), bulk.get(i), batched.get(i)));
            }
        }
    }

    private static void runPreparedStatementReuseTest() throws Exception {
        System.out.println("Running prepared statement reuse test...");
        long tA = 0;
        try (var conn = DatabaseManager.getInstance().getConnection()) {
            for (int i = 0; i < 1000; i++) {
                long s = System.currentTimeMillis();
                try (var ps = conn.prepareStatement("SELECT id FROM profesori WHERE id = ?")) {
                    ps.setInt(1, i);
                    try (var rs = ps.executeQuery()) { while (rs.next()) rs.getLong(1); }
                }
                tA += System.currentTimeMillis() - s;
            }
        }

        long tB = 0;
        try (var conn = DatabaseManager.getInstance().getConnection();
             var ps = conn.prepareStatement("SELECT id FROM profesori WHERE id = ?")) {
            for (int i = 0; i < 1000; i++) {
                long s = System.currentTimeMillis();
                ps.setInt(1, i);
                try (var rs = ps.executeQuery()) { while (rs.next()) rs.getLong(1); }
                tB += System.currentTimeMillis() - s;
            }
        }

        try (FileWriter fw = new FileWriter("reports/prepared_stmt.csv", false)) {
            fw.write("test,total_ms\n");
            fw.write("new_statements," + tA + "\n");
            fw.write("reused_statement," + tB + "\n");
        }
    }

    private static void writeCsv(String path, List<Long> email, List<Long> materie, List<Long> ageRange, List<Long> multi) throws IOException {
        try (FileWriter fw = new FileWriter(path, false)) {
            fw.write("run,email_ms,materie_ms,ageRange_ms,multi_ms\n");
            for (int i = 0; i < email.size(); i++) {
                fw.write(String.format("%d,%d,%d,%d,%d\n", i+1, email.get(i), materie.get(i), ageRange.get(i), multi.get(i)));
            }
        }
    }

    private static void queryByEmail(String email) throws SQLException {
        try (var conn = DatabaseManager.getInstance().getConnection();
             var ps = conn.prepareStatement("SELECT * FROM profesori WHERE email = ?")) {
            ps.setString(1, email);
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    rs.getLong("id");
                }
            }
        }
    }

    private static void queryByMaterie(long materieId) throws SQLException {
        try (var conn = DatabaseManager.getInstance().getConnection();
             var ps = conn.prepareStatement("SELECT * FROM profesori WHERE materie_id = ?")) {
            ps.setLong(1, materieId);
            try (var rs = ps.executeQuery()) {
                while (rs.next()) rs.getLong("id");
            }
        }
    }

    private static void queryByAgeRange(int min, int max) throws SQLException {
        try (var conn = DatabaseManager.getInstance().getConnection();
             var ps = conn.prepareStatement("SELECT * FROM profesori WHERE age BETWEEN ? AND ?")) {
            ps.setInt(1, min);
            ps.setInt(2, max);
            try (var rs = ps.executeQuery()) {
                while (rs.next()) rs.getLong("id");
            }
        }
    }

    private static void queryMulti(long materieId, int age) throws SQLException {
        try (var conn = DatabaseManager.getInstance().getConnection();
             var ps = conn.prepareStatement("SELECT * FROM profesori WHERE materie_id = ? AND age > ?")) {
            ps.setLong(1, materieId);
            ps.setInt(2, age);
            try (var rs = ps.executeQuery()) {
                while (rs.next()) rs.getLong("id");
            }
        }
    }

}
