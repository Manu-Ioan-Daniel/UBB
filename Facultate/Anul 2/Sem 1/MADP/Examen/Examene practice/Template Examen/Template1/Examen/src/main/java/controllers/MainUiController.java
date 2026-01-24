package controllers;

import enums.ChangeEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.MainService;
import utils.StageManager;
import utils.observer.Observer;

import java.util.List;

public class MainUiController implements Observer {
    @FXML
    private VBox root;

    private MainService mainService;
    private StageManager stageManager;

    @FXML
    private void handleRemove(){
        stageManager.showErrorAlert("CE FACI BAI BORFASULE?");
    }

    private Stage getStage(){
        return (Stage)root.getScene().getWindow();
    }

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }


    public void init(){
        stageManager = new StageManager();
        stageManager.showWindows(List.of("123","1234"),mainService);
    }

    @Override
    public void update(ChangeEvent event) {

    }
}
