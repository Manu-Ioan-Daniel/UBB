package bench;

import utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProfessorDataGenerator {

    public static void generate(int count) {
        String sql = "INSERT INTO profesori(name, age, email, materie_id) VALUES (?, ?, ?, ?)";
        Random rnd = new Random(42);
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            List<Long> materieIds = loadOrSeedMaterieIds(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 1; i <= count; i++) {
                    String name = "Prof_" + i;
                    int age = 25 + rnd.nextInt(40);
                    String email = "prof" + i + "@example.com";
                    long materieId = materieIds.get(rnd.nextInt(materieIds.size()));
                    ps.setString(1, name);
                    ps.setInt(2, age);
                    ps.setString(3, email);
                    ps.setLong(4, materieId);
                    ps.addBatch();
                    if (i % 500 == 0) {
                        ps.executeBatch();
                        conn.commit();
                    }
                }
                ps.executeBatch();
                conn.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static long getAnyMaterieId() {
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            List<Long> ids = loadOrSeedMaterieIds(conn);
            if (ids.isEmpty()) {
                throw new IllegalStateException("No materii available after seeding.");
            }
            return ids.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Long> loadOrSeedMaterieIds(Connection conn) throws SQLException {
        List<Long> ids = new ArrayList<>();
        String selectSql = "SELECT id FROM materii ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(selectSql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getLong(1));
            }
        }

        if (!ids.isEmpty()) {
            return ids;
        }

        // Seed a minimal set of materii if table is empty so FK constraints are satisfied.
        String insertSql = "INSERT INTO materii(name, credits) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            for (int i = 1; i <= 20; i++) {
                ps.setString(1, "Materie_" + i);
                ps.setInt(2, 4 + (i % 6));
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        }

        ids.clear();
        try (PreparedStatement ps = conn.prepareStatement(selectSql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getLong(1));
            }
        }
        return ids;
    }
}
