package repos;

import exceptions.ServiceException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.RollbackException;
import jakarta.persistence.TypedQuery;
import models.Professor;
import utils.HibernateConfig;
import utils.HibernateStats;

import java.util.List;

public class ProfessorDBRepoORM implements ProfessorRepository {

    private final EntityManagerFactory emf;

    public ProfessorDBRepoORM() {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    @Override
    public Professor findOne(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Professor p WHERE p.id = :id AND p.isDeleted = false", Professor.class)
                    .setParameter("id", id)
                    .getResultStream().findFirst().orElse(null);
        }
    }

    @Override
    public Professor findByEmail(String email) {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Professor p WHERE p.email = :email AND p.isDeleted = false", Professor.class)
                    .setParameter("email", email)
                    .getResultStream().findFirst().orElse(null);
        }
    }

    @Override
    public List<Professor> findAll() {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Professor p WHERE p.isDeleted = false", Professor.class).getResultList();
        }
    }

    @Override
    public List<Professor> findAllDeleted() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createNativeQuery(
                    "SELECT * FROM profesori WHERE is_deleted = true ORDER BY deleted_at DESC", Professor.class)
                    .getResultList();
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
        // keep backward compatibility: `delete` now means soft-delete
        softDelete(id, "system");
    }

    @Override
    public void softDelete(Long id, String deletedBy) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Professor p = em.find(Professor.class, id);
            if (p != null && Boolean.FALSE.equals(p.getIsDeleted())) {
                p.softDelete(deletedBy == null || deletedBy.isBlank() ? "system" : deletedBy);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public void restore(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("UPDATE profesori SET is_deleted = false, deleted_at = null, deleted_by = null WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Override
    public void hardDelete(Long id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM profesori WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Override
    public void update(Professor professor) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(professor);
            em.getTransaction().commit();
        } catch (OptimisticLockException ole) {
            try { if (em.getTransaction().isActive()) em.getTransaction().rollback(); } catch (Exception ignored) {}
            String msg = "Conflict detected: the Professor was modified by another user. Please reload and retry or force the update.";
            HibernateStats.log("OptimisticLockException", "Failed to update professor id=" + professor.getId() + ": " + ole);
            throw new ServiceException(msg);
        } catch (RollbackException re) {
            Throwable cause = re.getCause();
            if (cause instanceof OptimisticLockException) {
                try { if (em.getTransaction().isActive()) em.getTransaction().rollback(); } catch (Exception ignored) {}
                String msg = "Conflict detected: the Professor was modified by another user. Please reload and retry or force the update.";
                HibernateStats.log("RollbackException(Optimistic)", "Failed to update professor id=" + professor.getId() + ": " + re);
                throw new ServiceException(msg);
            } else {
                try { if (em.getTransaction().isActive()) em.getTransaction().rollback(); } catch (Exception ignored) {}
                HibernateStats.log("RollbackException", "Failed to update professor id=" + professor.getId() + ": " + re);
                throw new ServiceException("Database error while updating professor: " + re.getMessage());
            }
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    @Override
    public List<Professor> findPageByMaterieOffset(Long materieId, int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Professor> q = em.createQuery(
                    "SELECT p FROM Professor p WHERE p.materieId = :mid AND p.isDeleted = false ORDER BY p.id", Professor.class);
            q.setParameter("mid", materieId);
            q.setFirstResult(offset);
            q.setMaxResults(pageSize);
            return q.getResultList();
        }
    }

    @Override
    public List<Professor> findPageByMaterieAfter(Long materieId, Long lastId, int pageSize) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Professor> q = em.createQuery(
                    "SELECT p FROM Professor p WHERE p.materieId = :mid AND p.isDeleted = false AND p.id > :lastId ORDER BY p.id", Professor.class);
            q.setParameter("mid", materieId);
            q.setParameter("lastId", lastId == null ? 0L : lastId);
            q.setMaxResults(pageSize);
            return q.getResultList();
        }
    }
}
