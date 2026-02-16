package main;

import javafx.application.Application;
import javafx.stage.Stage;
import repo.UserRepo;
import services.MainService;
import utils.StageManager;


public class Main extends Application {

    @Override
    public void start(Stage stage){
        try {
            StageManager stageManager = new StageManager();
            MainService mainService = new MainService(new UserRepo());
            stageManager.showLoginWindow(stage,mainService);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
