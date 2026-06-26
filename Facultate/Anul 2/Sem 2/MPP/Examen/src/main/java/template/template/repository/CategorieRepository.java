package template.template.repository;

import lombok.RequiredArgsConstructor;
import template.template.domain.Categorie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategorieRepository {

    private final SessionFactory sessionFactory;

    public void save(Categorie entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        }
    }

    public Optional<Categorie> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Categorie.class, id));
        }
    }

    public Optional<Categorie> findByNume(String nume) {
        try (Session session = sessionFactory.openSession()) {
            Categorie entity = session.createQuery(
                            "FROM Categorie WHERE nume = :nume", Categorie.class)
                    .setParameter("nume", nume)
                    .uniqueResult();

            return Optional.ofNullable(entity);
        }
    }

    public List<Categorie> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Categorie", Categorie.class).list();
        }
    }

    public void update(Categorie entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        }
    }

    public void delete(Categorie entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(session.contains(entity) ? entity : session.merge(entity));
            tx.commit();
        }
    }
}
