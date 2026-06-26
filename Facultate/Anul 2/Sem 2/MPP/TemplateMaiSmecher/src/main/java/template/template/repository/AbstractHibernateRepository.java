package template.template.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.Optional;

public abstract class AbstractHibernateRepository<T, ID> implements BaseRepository<T, ID> {

    protected final SessionFactory sessionFactory;
    private final Class<T> entityClass;

    protected AbstractHibernateRepository(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    @Override
    public void save(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(entityClass, id));
        }
    }

    @Override
    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        }
    }

    @Override
    public void delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(session.contains(entity) ? entity : session.merge(entity));
            tx.commit();
        }
    }

    @Override
    public void deleteById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            String queryString = String.format("DELETE FROM %s WHERE id = :id", entityClass.getSimpleName());
            session.createMutationQuery(queryString)
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
        }
    }
}