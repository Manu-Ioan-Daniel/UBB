package utils.alerts;

import controller.ConfirmationAlertController;
import controller.ErrorAlertController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Alert {
    public static Stage confirmationAlert(Runnable action){
        try {
            FXMLLoader loader = new FXMLLoader(Alert.class.getResource("/view/confirmationAlert.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            ConfirmationAlertController controller = loader.getController();
            controller.setAction(action);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            return stage;
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    public static Stage errorAlert(String message){
        try {
            FXMLLoader loader = new FXMLLoader(Alert.class.getResource("/view/errorAlert.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            ErrorAlertController errorController = loader.getController();
            errorController.setErrorMessage(message);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);

            return stage;
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}


