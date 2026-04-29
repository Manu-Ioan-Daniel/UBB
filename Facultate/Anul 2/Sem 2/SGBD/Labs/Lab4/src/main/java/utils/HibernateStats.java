package utils;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

public class HibernateStats {

    public static void reset() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
        Statistics stats = sessionFactory.getStatistics();
        stats.clear();
    }

    public static Statistics snapshot() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
        return sessionFactory.getStatistics();
    }

}

