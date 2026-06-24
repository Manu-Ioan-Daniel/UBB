package org.example.repos;

import lombok.RequiredArgsConstructor;
import org.example.models.Config;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConfigRepository {

    private final SessionFactory sessionFactory;

    public List<Config> getConfigs(int n){
        try(Session session = sessionFactory.openSession()){
            List<Config> configs = session.createQuery("from Config", Config.class).list();
            int offset = (int) (Math.random() * configs.size() + 1);
            return configs.stream().filter(config -> config.getN() == n).limit(3).toList();
        }
    }
}
