package controllers;

import enums.ChangeEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import services.MainService;
import utils.observer.Observer;

public class WindowController implements Observer {
    @FXML
    private Label usernameLabel;

    private MainService mainService;

    public void setName(String name){
        usernameLabel.setText(name);
    }

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    @Override
    public void update(ChangeEvent event) {

    }
}
