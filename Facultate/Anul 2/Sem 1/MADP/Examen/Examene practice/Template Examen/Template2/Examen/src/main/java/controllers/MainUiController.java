package controllers;

import enums.ChangeEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.MainService;
import utils.StageManager;
import utils.observer.Observer;

public class MainUiController implements Observer {
    @FXML
    private VBox root;

    private MainService mainService;
    private StageManager stageManager;


    private Stage getStage(){
        return (Stage)root.getScene().getWindow();
    }

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }


    public void init(){
        stageManager = new StageManager();
    }

    @Override
    public void update(ChangeEvent event) {

    }
}
