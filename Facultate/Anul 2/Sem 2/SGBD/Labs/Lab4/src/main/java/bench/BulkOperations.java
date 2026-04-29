package bench;

import utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BulkOperations {

    private static final String TABLE = "profesori";

    public static long individualUpdates(int deptMaterieId, int limit) throws SQLException {
        List<Long> ids = new ArrayList<>();
        String selectSql = "SELECT id FROM " + TABLE + " WHERE materie_id = ? LIMIT ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(selectSql)) {
            ps.setInt(1, deptMaterieId);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) ids.add(rs.getLong(1));
            }
        }

        long t0 = System.currentTimeMillis();
        String updateSql = "UPDATE " + TABLE + " SET age = age + 1 WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psu = conn.prepareStatement(updateSql)) {
                for (Long id : ids) {
                    psu.setLong(1, id);
                    psu.executeUpdate();
                }
                conn.commit();
            }
        }
        long t1 = System.currentTimeMillis();
        return t1 - t0;
    }

    public static long bulkSqlUpdate(int materieId) throws SQLException {
        long t0 = System.currentTimeMillis();
        String updateSql = "UPDATE " + TABLE + " SET age = age + 1 WHERE materie_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(updateSql)) {
            ps.setInt(1, materieId);
            ps.executeUpdate();
        }
        long t1 = System.currentTimeMillis();
        return t1 - t0;
    }

    public static long batchedUpdates(int materieId, int batchSize, int limit) throws SQLException {
        List<Long> ids = new ArrayList<>();
        String selectSql = "SELECT id FROM " + TABLE + " WHERE materie_id = ? LIMIT ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(selectSql)) {
            ps.setInt(1, materieId);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) ids.add(rs.getLong(1));
            }
        }

        long t0 = System.currentTimeMillis();
        String updateSql = "UPDATE " + TABLE + " SET age = age + 1 WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psu = conn.prepareStatement(updateSql)) {
                int i = 0;
                for (Long id : ids) {
                    psu.setLong(1, id);
                    psu.addBatch();
                    i++;
                    if (i % batchSize == 0) {
                        psu.executeBatch();
                        conn.commit();
                    }
                }
                psu.executeBatch();
                conn.commit();
            }
        }
        long t1 = System.currentTimeMillis();
        return t1 - t0;
    }
}
