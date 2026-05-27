package org.example.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {

            LogManager.getLogManager().reset();
            Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}