package main;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.StageManager;



public class Main extends Application {

    @Override
    public void start(Stage stage){
        try {
            StageManager stageManager = new StageManager();
            stageManager.showMainWindow(stage);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
