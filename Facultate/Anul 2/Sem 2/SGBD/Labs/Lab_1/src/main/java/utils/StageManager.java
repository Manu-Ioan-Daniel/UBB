package utils;

import controllers.UiController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {
    public static void showMainWindow(){
        Tuple<Scene, UiController> tuple = FXMLUtils.load("/view/mainView.fxml");

        Stage stage = new Stage();
        stage.setScene(tuple.getFirst());
        stage.setTitle("Main Window");
        stage.show();
    }
}
