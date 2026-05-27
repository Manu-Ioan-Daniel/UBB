package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controllers.MainController;
import org.example.demonstratii.DirtyRead;
import org.example.demonstratii.PhantomRead;
import org.example.repos.ReportRepository;
import org.example.services.MainService;
import org.example.utils.HibernateUtil;
import org.hibernate.SessionFactory;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//            DirtyRead.demonstreaza();
//            PhantomRead.demonstreaza();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("JavaFX Application");
            MainController mainController = loader.getController();
            mainController.setMainService(new MainService(new ReportRepository()));
            mainController.init();
            stage.show();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}