package repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import models.Professor;
import utils.HibernateConfig;
import java.util.List;

public class ProfessorDBRepoORM implements ProfessorRepository {

    private final EntityManagerFactory emf;

    public ProfessorDBRepoORM() {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    @Override
    public Professor findOne(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Professor.class, id);
        }
    }

    @Override
    public Professor findByEmail(String email) {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("Select p from Professor p where p.email = :email", Professor.class)
                    .setParameter("email", email)
                    .getResultStream().findFirst().orElse(null);
        }
    }

    @Override
    public List<Professor> findAll() {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("Select p from Professor p", Professor.class).getResultList();
        }
    }

    @Override
    public void save(Professor professor) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(professor);
            em.getTransaction().commit();
        }
    }

    @Override
    public void delete(Long id) {
        try(EntityManager em = emf.createEntityManager()){
            Professor p = em.find(Professor.class, id);
            if(p != null){
                em.getTransaction().begin();
                em.remove(p);
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public void update(Professor professor) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(professor);
            em.getTransaction().commit();
        }
    }

    // OFFSET pagination
    public List<Professor> findPageOffset(int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Professor> q = em.createQuery("SELECT p FROM Professor p ORDER BY p.id", Professor.class);
            q.setFirstResult(offset);
            q.setMaxResults(pageSize);
            return q.getResultList();
        }
    }

    // KEYSET (cursor) pagination by id
    public List<Professor> findPageAfter(Long lastId, int pageSize) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Professor> q = em.createQuery("SELECT p FROM Professor p WHERE p.id > :lastId ORDER BY p.id", Professor.class);
            q.setParameter("lastId", lastId == null ? 0L : lastId);
            q.setMaxResults(pageSize);
            return q.getResultList();
        }
    }

    // Pagination methods scoped to materie
    @Override
    public List<Professor> findPageByMaterieOffset(Long materieId, int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Professor> q = em.createQuery("SELECT p FROM Professor p WHERE p.materieId = :mid ORDER BY p.id", Professor.class);
            q.setParameter("mid", materieId);
            q.setFirstResult(offset);
            q.setMaxResults(pageSize);
            return q.getResultList();
        }
    }

    @Override
    public List<Professor> findPageByMaterieAfter(Long materieId, Long lastId, int pageSize) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Professor> q = em.createQuery("SELECT p FROM Professor p WHERE p.materieId = :mid AND p.id > :lastId ORDER BY p.id", Professor.class);
            q.setParameter("mid", materieId);
            q.setParameter("lastId", lastId == null ? 0L : lastId);
            q.setMaxResults(pageSize);
            return q.getResultList();
        }
    }

}
