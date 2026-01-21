package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.MainService;
import utils.StageManager;

public class MainUiController{
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
        stageManager.showWindows(mainService.getUsernames());
    }

}
