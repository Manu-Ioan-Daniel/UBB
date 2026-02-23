package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ConfirmationAlertController  {

    @FXML
    private VBox root;

    private Runnable action;

    public void setAction(Runnable action) {
        this.action = action;
    }
    @FXML
    private Button noBtn;

    @FXML
    private Button yesBtn;

    @FXML
    private void noAction() {
        ((Stage) noBtn.getScene().getWindow()).close();
    }
    @FXML
    private void yesAction() {
        action.run();
        ((Stage) yesBtn.getScene().getWindow()).close();
    }


}
