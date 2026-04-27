package repos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import models.Student;
import utils.HibernateConfig;
import java.util.List;

public class StudRepoORM implements Repository<Long, Student>{

    private final EntityManagerFactory emf;

    public StudRepoORM() {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    @Override
    public Student findOne(Long id) {
        return null;
    }

    @Override
    public List<Student> findAll() {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("Select s from Student s", Student.class).getResultList();
        }
    }

    @Override
    public void save(Student student) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(Student student) {

    }
}
