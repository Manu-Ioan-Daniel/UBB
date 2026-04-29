package teste;

import repos.MaterieDBRepoORM;
import utils.HibernateStats;

import org.hibernate.stat.Statistics;
import java.util.List;
import models.Materie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import utils.HibernateConfig;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

public class NPlusOneDemo {

    public static void runDemo() {
        MaterieDBRepoORM repo = new MaterieDBRepoORM();

        System.out.println("--- N+1 demo: lazy access loop (within same EntityManager) ---");
        HibernateStats.reset();
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        long t0 = System.currentTimeMillis();
        int totalNotas = 0;
        int parentsCount = 0;
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            List<Materie> list = em.createQuery("SELECT m FROM Materie m", Materie.class).getResultList();
            parentsCount = Math.min(10, list.size());
            for (int i = 0; i < parentsCount; i++) { // document at least 10 parents
                Materie m = list.get(i);
                int size = m.getNotas() == null ? 0 : m.getNotas().size();
                totalNotas += size;
                System.out.println(m.getName() + " -> notas: " + size);
            }
            em.getTransaction().commit();
        }
        long t1 = System.currentTimeMillis();
        Statistics s1 = HibernateStats.snapshot();
        System.out.println("Time(ms): " + (t1 - t0));
        System.out.println("Total notas counted (N+1 run): " + totalNotas);
        System.out.println("Queries executed: " + s1.getQueryExecutionCount());
        System.out.println("PrepareStatements: " + s1.getPrepareStatementCount());

        System.out.println("--- JOIN FETCH demo ---");
        HibernateStats.reset();
        long t2 = System.currentTimeMillis();
        List<Materie> list2 = repo.findAllWithJoinFetchNotas();
        int total2 = 0;
        int parentsCount2 = Math.min(10, list2.size());
        for (int i = 0; i < parentsCount2; i++) {
            Materie m = list2.get(i);
            int size = m.getNotas() == null ? 0 : m.getNotas().size();
            total2 += size;
            System.out.println(m.getName() + " -> notas: " + size);
        }
        long t3 = System.currentTimeMillis();
        Statistics s2 = HibernateStats.snapshot();
        System.out.println("Time(ms): " + (t3 - t2));
        System.out.println("Total notas counted (JOIN FETCH): " + total2);
        System.out.println("Queries executed: " + s2.getQueryExecutionCount());
        System.out.println("PrepareStatements: " + s2.getPrepareStatementCount());

        Path reportsDir = Paths.get("reports");
        try {
            if (!Files.exists(reportsDir)) Files.createDirectories(reportsDir);
            Path out = reportsDir.resolve("nplus1_details.csv");
            boolean exists = Files.exists(out);
            try (FileWriter fw = new FileWriter(out.toFile(), true)) {
                if (!exists) {
                    fw.write("mode,queriesCount,timeMs\n");
                }
                fw.write(String.format("N+1,%d,%d\n",
                        s1.getPrepareStatementCount(),
                        (t1 - t0)
                ));

                fw.write(String.format("JOIN_FETCH,%d,%d\n",
                        s2.getPrepareStatementCount(),
                        (t3 - t2)
                ));
            }
        } catch (IOException ioe) {
            System.err.println("Failed to write nplus1_details.csv: " + ioe.getMessage());
        }

    }
}
