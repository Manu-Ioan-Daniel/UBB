package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WindowController {
    //nu uitam sa setam service uri aici

    @FXML
    private Label usernameLabel;

    public void setName(String name){
        usernameLabel.setText(name);
    }
}
