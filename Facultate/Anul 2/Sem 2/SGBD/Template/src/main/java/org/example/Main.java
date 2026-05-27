package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.utils.HibernateUtil;
import org.hibernate.SessionFactory;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("JavaFX Application");
            stage.show();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}