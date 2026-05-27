package org.example.demonstratii;

import org.example.models.Profesor;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PhantomRead {

    public static void demonstreaza() throws InterruptedException {

        Thread reader = new Thread(() -> {

            Session session = HibernateUtil.getSessionFactory().openSession();

            session.doWork(connection -> {
                connection.createStatement()
                        .execute("SET TRANSACTION ISOLATION LEVEL READ COMMITTED");
            });

            Transaction tx = session.beginTransaction();
            List<Profesor> profesori = session.createNativeQuery("SELECT * FROM profesori", Profesor.class).getResultList();
            System.out.println("[Reader] Numar profesori: " + profesori.size());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            profesori = session.createNativeQuery("SELECT * FROM profesori", Profesor.class).getResultList();
            System.out.println("[Reader] Numar profesori dupa 2 secunde: " + profesori.size());
            tx.commit();
            session.close();
        });

        Thread writer = new Thread(()->{
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Profesor p = new Profesor();
            p.setFirstName("NOU");
            p.setLastName("PROFESOR");
            p.setEmail("regele");
            p.setAge(50);
            session.persist(p);
            tx.commit();
            System.out.println("[Writer] Am adaugat un nou profesor!");
            session.close();
        });

        reader.start();
        writer.start();

        reader.join();
        writer.join();

    }

}
