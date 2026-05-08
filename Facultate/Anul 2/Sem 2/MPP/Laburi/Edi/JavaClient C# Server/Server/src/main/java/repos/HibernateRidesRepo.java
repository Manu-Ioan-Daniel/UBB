package repos;

import models.Ride;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class HibernateRidesRepo implements RidesRepo {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Optional<Ride> findOne(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Ride r = session.get(Ride.class, id);
            return Optional.ofNullable(r);
        }
    }

    @Override
    public List<Ride> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from models.Ride", Ride.class).list();
        }
    }

    @Override
    public void save(Ride entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

    @Override
    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Ride r = session.get(Ride.class, id);
            if (r != null) session.delete(r);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

    @Override
    public void update(Ride entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }
}

