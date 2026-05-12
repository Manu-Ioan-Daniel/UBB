package repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import models.Project;
import utils.HibernateConfig;

import java.util.List;

public class ProjectDBRepoORM implements Repository<Long, Project> {

    private final EntityManagerFactory emf;

    public ProjectDBRepoORM() {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    @Override
    public Project findOne(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Project.class, id);
        }
    }

    @Override
    public List<Project> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Project p", Project.class).getResultList();
        }
    }

    @Override
    public void save(Project project) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(project);
            em.getTransaction().commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Project project = em.find(Project.class, id);
            if (project != null) {
                em.getTransaction().begin();
                em.remove(project);
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public void update(Project project) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(project);
            em.getTransaction().commit();
        }
    }
}

