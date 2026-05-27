package org.example.demonstratii;

import org.example.models.Profesor;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DirtyRead {

    public static void demonstreaza() throws InterruptedException {


        Thread writer = new Thread(() -> {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            Profesor p = session.get(Profesor.class, 1L);
            System.out.println("[Writer] Valoare originala: " + p.getFirstName());

            p.setFirstName("MODIFICAT");
            session.merge(p);
            session.flush();

            System.out.println("[Writer] Am modificat dar NU fac commit, astept 5 secunde...");
            try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }

            tx.rollback();
            System.out.println("[Writer] Rollback facut!");
            session.close();
        });


        Thread reader = new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

            Session session = HibernateUtil.getSessionFactory().openSession();


            session.doWork(connection -> {
                connection.createStatement()
                        .execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            });

            Transaction tx = session.beginTransaction();
            Profesor p = session.get(Profesor.class, 1L);
            System.out.println("[Reader] Citeste valoarea: " + p.getFirstName());

            tx.commit();
            session.close();
        });

        writer.start();
        reader.start();

        writer.join();
        reader.join();
    }
}