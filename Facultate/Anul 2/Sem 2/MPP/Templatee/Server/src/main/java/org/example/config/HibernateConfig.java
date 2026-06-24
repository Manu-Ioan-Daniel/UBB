package org.example.config;

import org.example.models.Player; // Adaugă entitățile tale aici
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {
        Configuration configuration = new Configuration();

        // Proprietăți de conectare pentru MySQL
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/MPPExamen");
        configuration.setProperty("hibernate.connection.username", "root"); // Schimbă cu user-ul tău
        configuration.setProperty("hibernate.connection.password", ""); // Schimbă cu parola ta

        // Dialectul specific pentru MySQL
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        // CRUCIAL PENTRU EXAMEN: Activarea scrierii SQL-urilor generate în consolă
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");

        // Înregistrarea entităților (trebuie să ai minim două entități mapate cu ORM conform cerinței)
        configuration.addAnnotatedClass(Player.class);
        // configuration.addAnnotatedClass(Mutare.class);

        return configuration.buildSessionFactory();
    }
}