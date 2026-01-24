package main;

import javafx.application.Application;
import javafx.stage.Stage;
import repo.DriverDbRepository;
import repo.OrderDbRepository;
import services.MainService;
import utils.StageManager;
import validator.OrderValidator;


public class Main extends Application {

    @Override
    public void start(Stage stage){
        try {
            StageManager stageManager = new StageManager();
            MainService mainService = new MainService(new OrderDbRepository(),new DriverDbRepository(), new OrderValidator());
            stageManager.showMainWindow(stage,mainService);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


}
