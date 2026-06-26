package template.template.repository;

import lombok.RequiredArgsConstructor;
import template.template.domain.SampleEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SampleRepository {

    private final SessionFactory sessionFactory;

    public void save(SampleEntity entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        }
    }

    public Optional<SampleEntity> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(SampleEntity.class, id));
        }
    }

    public Optional<SampleEntity> findByName(String nume) {
        try (Session session = sessionFactory.openSession()) {
            SampleEntity entity = session.createQuery(
                            "FROM SampleEntity WHERE name = :nume", SampleEntity.class)
                    .setParameter("nume", nume)
                    .uniqueResult();

            return Optional.ofNullable(entity);
        }
    }

    public void update(SampleEntity entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        }
    }

    public void delete(SampleEntity entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(session.contains(entity) ? entity : session.merge(entity));
            tx.commit();
        }
    }

    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createMutationQuery("DELETE FROM SampleEntity WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
        }
    }
}