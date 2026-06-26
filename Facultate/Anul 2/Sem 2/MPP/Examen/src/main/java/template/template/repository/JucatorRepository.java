package template.template.repository;

import lombok.RequiredArgsConstructor;
import template.template.domain.Jucator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JucatorRepository {

    private final SessionFactory sessionFactory;

    public void save(Jucator entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        }
    }

    public Optional<Jucator> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Jucator.class, id));
        }
    }

    public Optional<Jucator> findByPorecla(String porecla) {
        try (Session session = sessionFactory.openSession()) {
            Jucator entity = session.createQuery(
                            "FROM Jucator WHERE porecla = :porecla", Jucator.class)
                    .setParameter("porecla", porecla)
                    .uniqueResult();

            return Optional.ofNullable(entity);
        }
    }

    public List<Jucator> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Jucator", Jucator.class).list();
        }
    }

    public void update(Jucator entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        }
    }

    public void delete(Jucator entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(session.contains(entity) ? entity : session.merge(entity));
            tx.commit();
        }
    }
}
