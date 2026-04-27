package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.OptimisticLockException;
import models.Professor;
import utils.HibernateConfig;

public class OptimisticLockDemo {
    public static void main(String[] args) throws InterruptedException {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        // Load same entity in two separate EntityManagers (simulating two users)
        EntityManager em1 = emf.createEntityManager();
        EntityManager em2 = emf.createEntityManager();

        try {
            em1.getTransaction().begin();
            Professor p1 = em1.find(Professor.class, 1L);
            System.out.println("User A loaded version: " + (p1 != null ? p1.getVersion() : "null"));

            em2.getTransaction().begin();
            Professor p2 = em2.find(Professor.class, 1L);
            System.out.println("User B loaded version: " + (p2 != null ? p2.getVersion() : "null"));

            // User A modifies and commits
            if (p1 != null) {
                p1.setPhone("+40-700-111111");
            }
            em1.getTransaction().commit();
            System.out.println("User A committed.");

            // User B modifies and attempts to commit
            if (p2 != null) {
                p2.setPhone("+40-700-222222");
            }
            try {
                em2.getTransaction().commit();
                System.out.println("User B committed (unexpected).");
            } catch (OptimisticLockException ole) {
                System.err.println("Optimistic lock detected for User B: " + ole.getMessage());
                ole.printStackTrace();
                // Provide options: reload, force, cancel (here we just reload)
                em2.getTransaction().rollback();
                em2.getTransaction().begin();
                Professor fresh = em2.find(Professor.class, 1L);
                System.out.println("Reloaded entity version: " + (fresh != null ? fresh.getVersion() : "null"));
                em2.getTransaction().commit();
            }

        } finally {
            if (em1.getTransaction().isActive()) em1.getTransaction().rollback();
            if (em2.getTransaction().isActive()) em2.getTransaction().rollback();
            em1.close();
            em2.close();
            HibernateConfig.close();
        }
    }
}

