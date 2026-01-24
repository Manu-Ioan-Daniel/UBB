package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class ErrorAlertController {
    @FXML
    private Label messageLabel;

    @FXML
    private VBox root;

    @FXML
    private void handleOk() {
        ((Stage)root.getScene().getWindow()).close();
    }

    public void setErrorMessage(String message) {
        messageLabel.setText(message);
    }


}
