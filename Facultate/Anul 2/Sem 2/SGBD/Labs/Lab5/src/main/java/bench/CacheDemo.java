package bench;

import repos.MaterieDBRepoORM;
import models.Materie;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CacheDemo {

    public static void runCacheTest() {
        try {
            Path reportsDir = Paths.get("reports");
            if (!Files.exists(reportsDir)) Files.createDirectories(reportsDir);
            Path out = reportsDir.resolve("cache_demo.csv");
            boolean exists = Files.exists(out);

            long anyId = ProfessorDataGenerator.getAnyMaterieId();

            MaterieDBRepoORM repo = new MaterieDBRepoORM();

            try (FileWriter fw = new FileWriter(out.toFile(), true)) {
                if (!exists) {
                    fw.write("step,action,timeMs,hits,misses\n");
                }

                long[] s0 = repo.getCacheStats();
                fw.write(String.format("initial,stats,0,%d,%d\n", s0[0], s0[1]));

                long t1 = System.currentTimeMillis();
                Materie m1 = repo.findOne(anyId);
                long d1 = System.currentTimeMillis() - t1;
                long[] s1 = repo.getCacheStats();
                fw.write(String.format("first_get,miss,%d,%d,%d\n", d1, s1[0], s1[1]));

                long t2 = System.currentTimeMillis();
                Materie m2 = repo.findOne(anyId);
                long d2 = System.currentTimeMillis() - t2;
                long[] s2 = repo.getCacheStats();
                fw.write(String.format("second_get,hit,%d,%d,%d\n", d2, s2[0], s2[1]));

                if (m2 != null) {
                    repo.update(m2);
                }
                long[] s3 = repo.getCacheStats();
                fw.write(String.format("after_invalidate,invalidated,0,%d,%d\n", s3[0], s3[1]));


                long t3 = System.currentTimeMillis();
                Materie m3 = repo.findOne(anyId);
                long d3 = System.currentTimeMillis() - t3;
                long[] s4 = repo.getCacheStats();
                fw.write(String.format("after_invalidate_get,miss_again,%d,%d,%d\n", d3, s4[0], s4[1]));

                fw.flush();
            }
            System.out.println("Cache demo written to reports/cache_demo.csv");
        } catch (IOException e) {
            System.err.println("Cache demo failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

