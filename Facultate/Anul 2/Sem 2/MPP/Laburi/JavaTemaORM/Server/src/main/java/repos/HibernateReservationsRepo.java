package repos;

import models.Reservation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class HibernateReservationsRepo implements ReservationsRepo {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Optional<Reservation> findOne(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Reservation r = session.get(Reservation.class, id);
            return Optional.ofNullable(r);
        }
    }

    @Override
    public List<Reservation> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from models.Reservation", Reservation.class).list();
        }
    }

    @Override
    public void save(Reservation entity) {
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
            Reservation r = session.get(Reservation.class, id);
            if (r != null) session.delete(r);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

    @Override
    public void update(Reservation entity) {
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

    @Override
    public List<Reservation> findReservationsByRideId(Long rideId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createNativeQuery(
                            "select * from reservations where ride_id = :rideId", Reservation.class)
                    .setParameter("rideId", rideId)
                    .list();
        }
    }
}
