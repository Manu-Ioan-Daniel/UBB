package controller;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import models.UserModel;



public class LoginController{

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label errorLabel;

    private UserModel userModel;

    public void login() {
        String user = username.getText();
        String pass = password.getText();
        if (user.isEmpty() || pass.isEmpty()){
            showError(username);
            showError(password);
            return;
        }
        if(!userModel.validLogin(user,pass)){
            showError(username);
            showError(password);
            return;
        }
        //open the actual ui window now
        System.out.println("Login succesful");


    }
    private void showError(TextField field) {
        if (!field.getStyleClass().contains("textfield-error")) {
            field.getStyleClass().add("textfield-error");
            errorLabel.setOpacity(1);
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            field.getStyleClass().remove("textfield-error");
            errorLabel.setOpacity(0);
        });
        pause.play();
    }

    public void setUserModel(UserModel model){
        this.userModel=model;
    }


}
