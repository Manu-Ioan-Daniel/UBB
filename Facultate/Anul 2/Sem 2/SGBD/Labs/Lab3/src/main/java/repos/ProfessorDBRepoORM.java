package repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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
        return null;
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
}
