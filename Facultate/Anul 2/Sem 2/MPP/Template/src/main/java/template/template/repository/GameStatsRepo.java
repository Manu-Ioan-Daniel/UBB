package template.template.repository;

import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import template.template.domain.StatisticiJoc;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GameStatsRepo {

    private final SessionFactory sessionFactory;

    public void save(StatisticiJoc statisticiJoc) {
        try(Session session = this.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(statisticiJoc);
            transaction.commit();
        }
    }

    public List<StatisticiJoc> getStatisticiByPlayer(String playerName) {
        try(Session session = this.sessionFactory.openSession()) {
           return session.createQuery("from StatisticiJoc where playerName = :playerName", StatisticiJoc.class).
                    setParameter("playerName", playerName)
                    .getResultList();
        }
    }
}
