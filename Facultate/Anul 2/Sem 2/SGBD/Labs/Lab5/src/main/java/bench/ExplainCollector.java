package bench;

import utils.DatabaseManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExplainCollector {

    public static void explainToFile(String sql, String outFile) {
        String explain = "EXPLAIN ANALYZE " + sql;
        File f = new File(outFile);
        f.getParentFile().mkdirs();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(explain);
             FileWriter fw = new FileWriter(f, false)) {
            while (rs.next()) {
                fw.write(rs.getString(1) + System.lineSeparator());
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
