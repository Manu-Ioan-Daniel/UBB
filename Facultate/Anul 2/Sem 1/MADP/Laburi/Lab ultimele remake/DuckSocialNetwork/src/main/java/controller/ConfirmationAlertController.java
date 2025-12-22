package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationAlertController implements Initializable {

    @FXML
    private VBox root;

    private Runnable action;
    private double offsetX = 0, offsetY = 0;

    public void setAction(Runnable action) {
        this.action = action;
    }
    @FXML
    private Button noBtn;

    @FXML
    private Button yesBtn;

    @FXML
    void noAction(ActionEvent event) {
        ((Stage) noBtn.getScene().getWindow()).close();
    }
    @FXML
    void yesAction(ActionEvent event) {
        action.run();
        ((Stage) yesBtn.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.setOnMousePressed(event ->{
            offsetX = event.getSceneX();
            offsetY = event.getSceneY();
        });

        root.setOnMouseDragged(event ->{
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(event.getScreenX() - offsetX);
            stage.setY(event.getScreenY() - offsetY);
        });
    }
}
