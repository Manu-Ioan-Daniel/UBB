package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.MainService;

public class MainUiController {
    @FXML
    private VBox root;

    private MainService mainService;

    private Stage getStage(){
        return (Stage)root.getScene().getWindow();
    }


    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }
}
