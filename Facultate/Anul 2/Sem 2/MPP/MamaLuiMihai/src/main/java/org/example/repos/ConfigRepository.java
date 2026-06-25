package org.example.repos;

import lombok.RequiredArgsConstructor;
import org.example.models.Config;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConfigRepository {

    private final SessionFactory sessionFactory;

    public List<Config> getConfigs(int n){
        try(var session = sessionFactory.openSession()){
            return session.createQuery("FROM Config", Config.class).list().stream()
                    .filter(c->c.getN() == n)
                    .limit(3)
                    .toList();
        }
    }
}
