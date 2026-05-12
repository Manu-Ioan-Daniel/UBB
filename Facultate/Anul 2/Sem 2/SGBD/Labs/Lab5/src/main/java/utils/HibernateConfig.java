package utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Properties;

public class HibernateConfig {

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory(){
        if(emf == null){
            Properties prop = new Properties();
            prop.put("jakarta.persistence.nonJtaDataSource", DatabaseManager.getInstance().getDataSource());
            prop.put("hibernate.show_sql", "false");
            prop.put("hibernate.format_sql", "false");
            prop.put("hibernate.use_sql_comments", "false");
            emf = Persistence.createEntityManagerFactory("myapp", prop);
        }
        return emf;
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
