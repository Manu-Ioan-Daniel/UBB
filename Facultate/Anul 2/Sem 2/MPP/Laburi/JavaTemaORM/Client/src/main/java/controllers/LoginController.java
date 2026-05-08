package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import proxy.ServicesObjectProxy;
import services.IAuthService;

public class LoginController{

    private IAuthService auth;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void handleLogin() {
        if(auth.authenticate(usernameField.getText(),passwordField.getText())){
                utils.StageManager.openMainWindow(new ServicesObjectProxy("127.0.0.1",5555));
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

    public void setAuth(IAuthService auth) {
        this.auth = auth;
    }

}
