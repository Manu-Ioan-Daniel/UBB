package utils;

import controllers.ConfirmationAlertController;
import controllers.ErrorAlertController;
import controllers.InformationAlertController;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AlertFactory {
    private static AlertFactory instance = null;

    private AlertFactory() {
    }

    public static AlertFactory getInstance() {
        if (instance == null) {
            instance = new AlertFactory();
        }
        return instance;
    }

    public Stage confirmationAlert(Runnable action) {

        Tuple<Scene, ConfirmationAlertController> tuple = FXMLUtil.load("/view/confirmationAlert.fxml");
        tuple.getSecond().setAction(action);
        Stage stage = new Stage();
        stage.setScene(tuple.getFirst());
        return stage;

    }

    public Stage errorAlert(String message) {
        Tuple<Scene, ErrorAlertController> tuple = FXMLUtil.load("/view/errorAlert.fxml");
        tuple.getSecond().setErrorMessage(message);
        Stage stage = new Stage();
        stage.setScene(tuple.getFirst());
        return stage;
    }

    public Stage informationAlert(String message) {
        Tuple<Scene, InformationAlertController> tuple = FXMLUtil.load("/view/informationAlert.fxml");
        tuple.getSecond().setMessage(message);
        Stage stage = new Stage();
        stage.setScene(tuple.getFirst());
        return stage;
    }
}