package utils;

import controllers.MainUiController;
import controllers.WindowController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.MainService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


public class StageManager {

    private final Map<String, Stage> openStages = new HashMap<>();

    private void showStageOnce(String key, Supplier<Stage> stageSupplier) {
        Stage stage = openStages.get(key);
        if (stage != null) {
            stage.toFront();
            return;
        }
        stage = stageSupplier.get();
        openStages.put(key, stage);
        stage.setOnHidden(ev->openStages.remove(key));
        stage.show();
    }

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

    public void showErrorAlert(String message) {
        showStageReplace("errorAlert",()->AlertFactory.getInstance().errorAlert(message));
    }

    public void showConfirmationAlert(Runnable action) {
        showStageOnce("confirmationAlert",()->AlertFactory.getInstance().confirmationAlert(action));
    }
    public void showInformationAlert(String message){
        showStageReplace("informationAlert",()->AlertFactory.getInstance().informationAlert(message));
    }

    public void showWindows(List<String> name,MainService mainService){
        for(String nume : name) {
            Tuple<Scene, WindowController> tuple = FXMLUtil.load("/view/window.fxml");
            tuple.getSecond().setName(nume);
            tuple.getSecond().setMainService(mainService);
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
