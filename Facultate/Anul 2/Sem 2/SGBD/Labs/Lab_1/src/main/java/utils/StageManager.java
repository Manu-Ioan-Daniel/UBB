package utils;

import controllers.UiController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.Service;

public class StageManager {
    public static void showMainWindow(Service service){
        Tuple<Scene, UiController> tuple = FXMLUtils.load("/view/mainView.fxml");

        tuple.getSecond().setService(service);
        tuple.getSecond().init();

        Stage stage = new Stage();
        stage.setScene(tuple.getFirst());
        stage.setTitle("Main Window");
        stage.show();
    }
}
