package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import services.Service;
import utils.StageManager;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private CheckBox adminCheck;

    private Service service;
    private StageManager stageManager;

    public void setService(Service service) {
        this.service = service;
    }

    public void setStageManager(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField == null ? "anonymous" : usernameField.getText();
        boolean isAdmin = adminCheck != null && adminCheck.isSelected();
        service.setCurrentUser(username, isAdmin);

        stageManager.closeLoginWindow();
        StageManager.showMainWindow(service, stageManager);
    }
}

