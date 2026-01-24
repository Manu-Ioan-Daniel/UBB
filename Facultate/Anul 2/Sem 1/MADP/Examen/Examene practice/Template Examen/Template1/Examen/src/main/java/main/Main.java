package main;

import javafx.application.Application;
import javafx.stage.Stage;
import repo.NotificationRepo;
import services.MainService;
import utils.StageManager;
import validator.NotificationValidator;


public class Main extends Application {

    @Override
    public void start(Stage stage){
        try {
            StageManager stageManager = new StageManager();
            MainService mainService = new MainService(new NotificationRepo(),new NotificationValidator());
            stageManager.showMainWindow(stage,mainService);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


}
