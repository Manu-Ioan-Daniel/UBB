package template.template.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import template.template.domain.Config;
import template.template.domain.Player;

@org.springframework.context.annotation.Configuration
public class HibernateUtils {

    @Bean
    public SessionFactory sessionFactory() {
        return new Configuration()
                .addAnnotatedClass(Player.class)
                .addAnnotatedClass(Config.class)
                .buildSessionFactory();
    }
}
