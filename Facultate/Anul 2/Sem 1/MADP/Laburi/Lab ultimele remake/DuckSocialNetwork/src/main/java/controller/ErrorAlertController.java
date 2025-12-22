package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorAlertController {
    @FXML
    private Label messageLabel;

    @FXML
    private Button okBtn;

    @FXML
    private VBox root;

    @FXML
    void handleOk(ActionEvent event) {
        ((Stage)root.getScene().getWindow()).close();
    }

    public void setErrorMessage(String message) {
        messageLabel.setText(message);
    }
}
