package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.RollbackException;
import models.Professor;

import java.util.Scanner;

public class OptimisticLockingDemo {

    public static void main(String[] args) {
        runDemo();
    }

    public static void runDemo() {
        final long professorId = 1L;
        final String bPhone = "B-" + System.currentTimeMillis();
        final String aStalePhone = "A-STALE-" + System.nanoTime();
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        EntityManager emA = emf.createEntityManager();
        EntityManager emB = emf.createEntityManager();
        EntityManager emC = emf.createEntityManager();

        try {
            emA.getTransaction().begin();
            Professor staleFromA = emA.find(Professor.class, professorId);
            emA.getTransaction().commit();

            if (staleFromA == null) {
                System.out.println("[Demo] Profesorul id=" + professorId + " nu exista.");
                return;
            }

            emA.detach(staleFromA);
            System.out.println("[Demo] A si B au incarcat acelasi profesor. versiune=" + staleFromA.getVersion());

            emB.getTransaction().begin();
            Professor freshB = emB.find(Professor.class, professorId);
            freshB.setPhone(bPhone);
            emB.getTransaction().commit();
            System.out.println("[Demo] B a salvat cu succes. versiune noua=" + freshB.getVersion());

            staleFromA.setPhone(aStalePhone);
            try {
                emC.getTransaction().begin();
                emC.merge(staleFromA);
                emC.getTransaction().commit();
                System.out.println("[Demo] A a salvat fara conflict - neasteptat.");
            } catch (OptimisticLockException | RollbackException ex) {
                safeRollback(emC);
                handleConflict(emf, professorId, ex);
            }

        } finally {
            safeRollback(emA);
            safeRollback(emB);
            safeRollback(emC);
            emA.close();
            emB.close();
            emC.close();
        }
    }

    private static void handleConflict(EntityManagerFactory emf, long professorId, Exception ex) {
        String friendly = "Conflict de versiune: alt utilizator a modificat deja inregistrarea. " +
                "Alege: 1) reload, 2) update fortat, 3) anulare.";
        System.out.println("\n[Conflict] " + friendly);
        HibernateStats.log("OptimisticLockConflict", "professorId=" + professorId + ", error=" + ex);

        System.out.println("1) Reload date curente");
        System.out.println("2) Actualizare fortata (suprascrie)");
        System.out.println("3) Anulare");
        System.out.print("Optiune [1-3]: ");

        String choice = "3";
        try {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null && !line.trim().isEmpty()) {
                    choice = line.trim();
                }
            } else {
                System.out.println("\n[Conflict] Input indisponibil in acest run. Se aplica implicit: anulare.");
            }
        } catch (Exception inputEx) {
            HibernateStats.log("OptimisticLockInput", "Input unavailable: " + inputEx);
            System.out.println("\n[Conflict] Input indisponibil. Se aplica implicit: anulare.");
        }

        switch (choice) {
            case "1" -> reloadCurrent(emf, professorId);
            case "2" -> forceUpdate(emf, professorId);
            default -> System.out.println("[Conflict] Operatie anulata.");
        }
    }

    private static void reloadCurrent(EntityManagerFactory emf, long professorId) {
        try (EntityManager em = emf.createEntityManager()) {
            Professor current = em.find(Professor.class, professorId);
            if (current == null) {
                System.out.println("[Reload] Profesorul nu mai exista.");
                return;
            }
            System.out.println("[Reload] Date curente: id=" + current.getId()
                    + ", email=" + current.getEmail()
                    + ", phone=" + current.getPhone()
                    + ", version=" + current.getVersion());
        }
    }

    private static void forceUpdate(EntityManagerFactory emf, long professorId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            int updated = em.createNativeQuery("UPDATE profesori SET phone = :phone, version = version + 1 WHERE id = :id")
                    .setParameter("phone", "0700-000-002-FORCED")
                    .setParameter("id", professorId)
                    .executeUpdate();
            em.getTransaction().commit();

            if (updated > 0) {
                System.out.println("[Force] Update fortat aplicat.");
                HibernateStats.log("OptimisticLockForceUpdate", "Forced update applied for professorId=" + professorId);
            } else {
                System.out.println("[Force] Nicio inregistrare actualizata.");
            }
        } catch (Exception e) {
            System.out.println("[Force] Eroare la update fortat: " + e.getMessage());
            HibernateStats.log("OptimisticLockForceUpdateError", "professorId=" + professorId + ", error=" + e);
        }
    }

    private static void safeRollback(EntityManager em) {
        try {
            if (em != null && em.isOpen() && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } catch (Exception ignored) {
        }
    }
}
