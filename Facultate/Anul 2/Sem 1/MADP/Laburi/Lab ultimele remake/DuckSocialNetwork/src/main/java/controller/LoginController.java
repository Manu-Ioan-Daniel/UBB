package controller;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.UserModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private HBox root;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginBtn;

    private UserModel userModel;


    @FXML
    public void login(ActionEvent event){
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(!userModel.validLogin(username,password)){
            showError(usernameField);
            showError(passwordField);
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/usersForm.fxml"));
        Parent newRoot;
        try {
            newRoot = fxmlLoader.load();
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        UsersFormController usersFormController = fxmlLoader.getController();
        usersFormController.initData(username, userModel);

        Scene scene = new Scene(newRoot);

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();


    }
    private void showError(TextField field) {
        if (!field.getStyleClass().contains("textfield-error")) {
            field.getStyleClass().add("textfield-error");
            errorLabel.setOpacity(1);
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            field.getStyleClass().remove("textfield-error");
            errorLabel.setOpacity(0);
        });
        pause.play();
    }


    public void setUserModel(UserModel model){
        this.userModel=model;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setDefaultButton(true);
    }


}
