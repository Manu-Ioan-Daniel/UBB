package main;

import javafx.application.Application;
import javafx.stage.Stage;
import repo.DriverDbRepository;
import repo.NotificationRepo;
import repo.OrderDbRepository;
import services.MainService;
import utils.StageManager;
import validator.NotificationValidator;
import validator.OrderValidator;


public class Main extends Application {

    @Override
    public void start(Stage stage){
        try {
            StageManager stageManager = new StageManager();
            MainService mainService = new MainService(new NotificationRepo(),new OrderDbRepository(),new DriverDbRepository(), new NotificationValidator(),new OrderValidator());
            stageManager.showMainWindow(stage,mainService);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


}
