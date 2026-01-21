package main;

import javafx.application.Application;
import javafx.stage.Stage;
import repo.NotificationRepo;
import services.MainService;
import services.NotificationService;
import utils.StageManager;



public class Main extends Application {

    @Override
    public void start(Stage stage){
        try {
            StageManager stageManager = new StageManager();
            MainService mainService = new MainService(new NotificationService(new NotificationRepo()));
            stageManager.showMainWindow(stage,mainService);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
