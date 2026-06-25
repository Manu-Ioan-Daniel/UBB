package template.template.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import template.template.domain.Config;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConfigRepository {

    private final SessionFactory sessionFactory;

    public List<Config> getConfigs(int n){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from Config where n=:n",  Config.class).setParameter("n", n).list().stream().limit(3).toList();
        }
    }

    public void save(Config config){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(config);
            transaction.commit();
        }
    }

}
