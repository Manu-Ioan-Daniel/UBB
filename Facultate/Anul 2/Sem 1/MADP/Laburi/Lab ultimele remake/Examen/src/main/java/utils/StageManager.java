package utils;

import controllers.MainUiController;
import controllers.WindowController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.MainService;

import java.util.List;


public class StageManager {

    public void showWindows(List<String> name){
        for(String nume : name) {
            Tuple<Scene, WindowController> tuple = FXMLUtil.load("/view/window.fxml");
            tuple.getSecond().setName(nume);
            Scene scene = tuple.getFirst();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void showMainWindow(Stage stage, MainService mainService){
        Tuple<Scene, MainUiController> tuple = FXMLUtil.load(("/view/mainUi.fxml"));
        tuple.getSecond().setMainService(mainService);
        tuple.getSecond().init();
        Scene scene = tuple.getFirst();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
