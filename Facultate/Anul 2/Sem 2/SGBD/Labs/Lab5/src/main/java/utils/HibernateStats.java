package utils;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HibernateStats {

    public static void reset() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
        Statistics stats = sessionFactory.getStatistics();
        stats.clear();
    }

    public static Statistics snapshot() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
        return sessionFactory.getStatistics();
    }

    /**
     * Append a small log line to logs/hibernate_stats.log but do NOT flood the console.
     */
    public static void log(String tag, String message) {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String line = String.format("[%s] %s: %s\n", ts, tag, message);
        // Write to file only to avoid flooding the console
        try {
            File dir = new File("logs");
            if (!dir.exists()) dir.mkdirs();
            try (FileWriter fw = new FileWriter(new File(dir, "hibernate_stats.log"), true)) {
                fw.write(line);
            }
        } catch (IOException e) {
            // best effort: in case of failure, write minimal message to stderr
            System.err.println("[HibernateStats] failed to write log: " + e.getMessage());
        }
    }

}
