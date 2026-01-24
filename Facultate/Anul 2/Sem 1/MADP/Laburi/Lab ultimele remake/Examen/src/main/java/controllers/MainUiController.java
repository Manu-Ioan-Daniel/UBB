package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.MainService;
import utils.StageManager;



public class MainUiController{
    @FXML
    private VBox root;

    @FXML
    private TextField clientNameLabel;

    @FXML
    private TextField destinationAdressLabel;

    @FXML
    private Button handleAdd;

    @FXML
    private TextField pickupAdressLabel;

    private MainService mainService;
    private StageManager stageManager;

    @FXML
    private void handleAdd(){
        try {
            String clientName = clientNameLabel.getText();
            String pickupAdress = pickupAdressLabel.getText();
            String destinationAdress = destinationAdressLabel.getText();
            mainService.addOrder(clientName, pickupAdress, destinationAdress);
        }catch (Exception e){
            stageManager.showErrorAlert(e.getMessage());
        }
    }

    private Stage getStage(){
        return (Stage)root.getScene().getWindow();
    }

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }


    public void init(){
        stageManager = new StageManager();
        stageManager.showWindow(mainService.getAllDrivers(), mainService);
    }

}
