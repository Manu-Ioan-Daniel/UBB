package bench;

import repos.ProfessorDBRepoORM;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PaginationBench {

    public static void runPaginationBench() throws IOException {
        ProfessorDBRepoORM repo = new ProfessorDBRepoORM();
        ProfessorDataGenerator.generate(10000);

        int pageSize = 100;

        int totalFetched = repo.findAll().size();
        int totalPages = Math.max(1, (totalFetched + pageSize - 1) / pageSize);

        long t0 = System.currentTimeMillis();
        List<?> first = repo.findPageOffset(0, pageSize);
        long dtFirst = System.currentTimeMillis() - t0;

        int midPage = Math.max(0, totalPages / 2);
        long t1 = System.currentTimeMillis();
        List<?> mid = repo.findPageOffset(midPage, pageSize);
        long dtMid = System.currentTimeMillis() - t1;

        int lastPage = Math.max(0, totalPages - 1);
        long t2 = System.currentTimeMillis();
        List<?> last = repo.findPageOffset(lastPage, pageSize);
        long dtLast = System.currentTimeMillis() - t2;

        try (FileWriter fw = new FileWriter("reports/pagination-offset.csv", false)) {
            fw.write("pageType,rows,timeMs\n");
            fw.write("first," + first.size() + "," + dtFirst + "\n");
            fw.write("mid," + mid.size() + "," + dtMid + "\n");
            fw.write("last," + last.size() + "," + dtLast + "\n");
        }

        long kt0 = System.currentTimeMillis();
        List<?> kfirst = repo.findPageAfter(null, pageSize); // after null -> from start
        long kdtFirst = System.currentTimeMillis() - kt0;

        // advance to middle using keyset
        Long lastId = null;
        for (int i = 0; i < midPage; i++) {
            List<?> page = repo.findPageAfter(lastId, pageSize);
            if (page.isEmpty()) break;
            Object lastObj = page.get(page.size()-1);
            try {
                java.lang.reflect.Method m = lastObj.getClass().getMethod("getId");
                lastId = (Long) m.invoke(lastObj);
            } catch (Exception e) {
                lastId = null;
                break;
            }
        }

        long kt1 = System.currentTimeMillis();
        List<?> kmid = repo.findPageAfter(lastId, pageSize);
        long kdtMid = System.currentTimeMillis() - kt1;

        // advance to last using keyset
        Long cursor = null;
        for (int i = 0; i < totalPages; i++) {
            List<?> p = repo.findPageAfter(cursor, pageSize);
            if (p.isEmpty()) { break; }
            Object lastObj = p.get(p.size()-1);
            try {
                java.lang.reflect.Method m = lastObj.getClass().getMethod("getId");
                cursor = (Long) m.invoke(lastObj);
            } catch (Exception e) {
                break;
            }
            // continue until we reach last
        }
        long kt2 = System.currentTimeMillis();
        long kdtLast = kt2 - kt1;
        List<?> kLast = repo.findPageAfter(cursor, pageSize);

        try (FileWriter fw = new FileWriter("reports/pagination-keyset.csv", false)) {
            fw.write("pageType,rows,timeMs\n");
            fw.write("first," + kfirst.size() + "," + kdtFirst + "\n");
            fw.write("mid," + (kmid == null ? 0 : kmid.size()) + "," + kdtMid + "\n");
            fw.write("last," + (kLast == null ? 0 : kLast.size()) + "," + kdtLast + "\n");
        }
    }
}
