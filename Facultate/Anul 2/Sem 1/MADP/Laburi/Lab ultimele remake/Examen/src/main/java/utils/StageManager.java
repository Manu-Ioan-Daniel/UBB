package utils;

import controllers.MainUiController;
import controllers.WindowController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.MainService;


public class StageManager {

    public void showWindow(){
        Tuple<Scene, WindowController> tuple = FXMLUtil.load("/view/window.fxml");
        Scene scene = tuple.getFirst();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void showMainWindow(Stage stage, MainService mainService){
        Tuple<Scene, MainUiController> tuple = FXMLUtil.load(("/view/mainUi.fxml"));
        tuple.getSecond().setMainService(mainService);
        Scene scene = tuple.getFirst();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
