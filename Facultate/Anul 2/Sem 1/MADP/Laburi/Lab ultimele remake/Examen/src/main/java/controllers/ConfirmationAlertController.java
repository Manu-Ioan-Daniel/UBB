package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class ConfirmationAlertController  {


    @FXML
    private Label message;

    private Runnable action;


    public void setAction(Runnable action) {
        this.action = action;
    }


    @FXML
    private Button yesBtn;

    @FXML
    private void yesAction() {
        action.run();
        ((Stage) yesBtn.getScene().getWindow()).close();
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }


}
