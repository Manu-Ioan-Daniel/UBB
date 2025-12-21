package controller;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;


public class LoginController{

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label errorLabel;


    public void login() {
        String user = username.getText();
        String pass = password.getText();
        if (user.isEmpty() || pass.isEmpty()){
            showError(username);
            showError(password);
        }

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

}
