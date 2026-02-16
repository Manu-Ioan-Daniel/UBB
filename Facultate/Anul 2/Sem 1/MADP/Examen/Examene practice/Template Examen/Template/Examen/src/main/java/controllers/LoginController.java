package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.MainService;
import utils.StageManager;

public class LoginController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    private MainService mainService;
    private final StageManager stageManager = new StageManager();

    public void handleLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(username.isEmpty() || password.isEmpty()){
            stageManager.showErrorAlert("Username and password cannot be empty!");
            return;
        }
        var loginResult = mainService.validLogin(username,password);
        if(loginResult.getFirst()){
            if(loginResult.getSecond().equals("admin")){
                //stageManager.showAdminWindow(getStage(), mainService);
            }else{
                //stageManager.showNormalUserWindow(getStage(), mainService);
            }
        }
        else{
            stageManager.showErrorAlert("Invalid username or password!");
        }
    }

    public void setMainService(MainService mainService){
        this.mainService = mainService;
    }

    private Stage getStage(){
        return (Stage) root.getScene().getWindow();
    }
}
