package repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import models.Materie;
import utils.HibernateConfig;
import java.util.List;

public class MaterieDBRepoORM implements Repository<Long, Materie>{

    private final EntityManagerFactory emf;

    public MaterieDBRepoORM() {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    @Override
    public Materie findOne(Long id) {
        return null;
    }

    @Override
    public List<Materie> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Materie m", Materie.class).getResultList();
        }
    }

    @Override
    public void save(Materie materie) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Materie materie) {

    }

}
