package template.template.repository;

import lombok.RequiredArgsConstructor;
import template.template.domain.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SampleRepository {

    private final SessionFactory sessionFactory;

    public void save(Player player) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(player);
            tx.commit();
        }
    }

    public Optional<Player> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Player.class, id));
        }
    }

    public Optional<Player> findByName(String nume) {
        try (Session session = sessionFactory.openSession()) {
            Player entity = session.createQuery(
                            "FROM Player WHERE name = :nume", Player.class)
                    .setParameter("nume", nume)
                    .uniqueResult();

            return Optional.ofNullable(entity);
        }
    }

    public void update(Player entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        }
    }

    public void delete(Player entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(session.contains(entity) ? entity : session.merge(entity));
            tx.commit();
        }
    }

    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createMutationQuery("DELETE FROM Player WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
        }
    }
}