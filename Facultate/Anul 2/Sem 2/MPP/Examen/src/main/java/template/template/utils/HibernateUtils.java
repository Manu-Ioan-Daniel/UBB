package template.template.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import template.template.domain.Jucator;
import template.template.domain.Categorie;

@org.springframework.context.annotation.Configuration
public class HibernateUtils {

    @Bean
    public SessionFactory sessionFactory() {
        return new Configuration()
                .addAnnotatedClass(Jucator.class)
                .addAnnotatedClass(Categorie.class)
                .buildSessionFactory();
    }
}
