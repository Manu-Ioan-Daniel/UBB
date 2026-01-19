package utils;

import controllers.MainUiController;
import controllers.WindowController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class StageManager {

    private final Map<String, Stage> openStages = new HashMap<>();

    private void showStageReplace(String key, Supplier<Stage> stageSupplier) {
        Stage stage = openStages.get(key);
        if (stage != null) {
            stage.close();
        }
        stage = stageSupplier.get();
        openStages.put(key, stage);
        stage.setOnHidden(ev->openStages.remove(key));
        stage.show();
    }

    public void showWindow(){
        showStageReplace("window",()->{
            Tuple<Scene, WindowController> tuple = FXMLUtil.load("/view/window.fxml");
            Scene scene = tuple.getFirst();
            Stage stage = new Stage();
            stage.setScene(scene);
            return stage;
        });
    }
    public void showMainWindow(Stage stage){
        Tuple<Scene, MainUiController> tuple = FXMLUtil.load(("/view/mainUi.fxml"));
        Scene scene = tuple.getFirst();
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
