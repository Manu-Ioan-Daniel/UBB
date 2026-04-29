package repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import models.Materie;
import utils.HibernateConfig;
import utils.SimpleEntityCache;
import java.util.List;

public class MaterieDBRepoORM implements Repository<Long, Materie>{

    private final EntityManagerFactory emf;
    private final SimpleEntityCache<Long, Materie> cache = new SimpleEntityCache<>(5 * 60 * 1000); // 5 min TTL

    public MaterieDBRepoORM() {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    @Override
    public Materie findOne(Long id) {
        Materie cached = cache.get(id);
        if (cached != null) return cached;
        try (EntityManager em = emf.createEntityManager()) {
            Materie m = em.find(Materie.class, id);
            if (m != null) cache.put(id, m);
            return m;
        }
    }

    @Override
    public List<Materie> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Materie m", Materie.class).getResultList();
        }
    }

    public List<Materie> findAllWithJoinFetchNotas() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Materie> q = em.createQuery("SELECT DISTINCT m FROM Materie m LEFT JOIN FETCH m.notas", Materie.class);
            return q.getResultList();
        }
    }

    // Return list but initialize lazy collection while EntityManager is open to avoid LazyInitializationException
    public List<Materie> findAllLazyAccessNotas() {
        try (EntityManager em = emf.createEntityManager()) {
            List<Materie> list = em.createQuery("SELECT m FROM Materie m", Materie.class).getResultList();
            // Initialize notas for each materie while EM is open
            for (Materie m : list) {
                if (m.getNotas() != null) {
                    m.getNotas().size(); // triggers initialization
                }
            }
            return list;
        }
    }

    @Override
    public void save(Materie materie) {

    }

    @Override
    public void delete(Long id) {
        cache.invalidate(id);
    }

    @Override
    public void update(Materie materie) {
        cache.invalidate(materie.getId());
    }

    // expose cache stats: returns long[]{hits, misses}
    public long[] getCacheStats() {
        return new long[]{cache.getHits(), cache.getMisses()};
    }

}
