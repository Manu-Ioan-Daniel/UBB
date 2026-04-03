package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.AuthService;

public class LoginController{

    private AuthService auth;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void handleLogin() {
        if(auth.authenticate(usernameField.getText(),passwordField.getText())){
                utils.StageManager.openMainWindow();
                ((javafx.stage.Stage) usernameField.getScene().getWindow()).close();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Credentials");
            alert.showAndWait();
            usernameField.setStyle("-fx-border-color: red;");
            passwordField.setStyle("-fx-border-color: red;");
        }

    }

    public void setAuth(AuthService auth) {
        this.auth = auth;
    }

}
