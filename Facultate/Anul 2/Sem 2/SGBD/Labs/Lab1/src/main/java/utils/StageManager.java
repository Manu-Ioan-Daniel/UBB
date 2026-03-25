package utils;

import controllers.AddFormController;
import controllers.BaseFormController;
import controllers.UiController;
import controllers.UpdateFormController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Professor;
import services.Service;

import java.util.HashMap;
import java.util.Map;

public class StageManager {

    private final Map<String,Stage> openStages = new HashMap<>();

    /***
     * Afiseaza fereastra principala a aplicatiei
     * @param service serviciul care va fi folosit de controllerul ferestrei principale
     * @param stageManager managerul de stadii care va fi folosit de controllerul ferestrei principale pentru a deschide alte ferestre
     */
    public static void showMainWindow(Service service, StageManager stageManager){
        Tuple<Scene, UiController> tuple = FXMLUtils.load("/view/mainView.fxml");

        tuple.getSecond().setService(service);
        tuple.getSecond().setStageManager(stageManager);
        tuple.getSecond().init();

        Stage stage = new Stage();
        stage.setScene(tuple.getFirst());
        stage.setTitle("Main Window");
        stage.show();
    }

    /***
     * Afiseaza fereastra de adaugare a unui profesor
     * @param service serviciul care va fi folosit de controllerul ferestrei de adaugare pentru a adauga un profesor
     * @param materieId id ul materiei la care se va adauga profesorul
     */
    public void showAddFormWindow(Service service, Long materieId){
        Tuple<Scene, AddFormController> tuple = FXMLUtils.load("/view/addForm.fxml");

        tuple.getSecond().setService(service);
        tuple.getSecond().init(materieId);

        if(openStages.containsKey("addForm")){
            openStages.get("addForm").close();
            openStages.remove("addForm");
        }
        Stage stage = new Stage();
        stage.setScene(tuple.getFirst());
        stage.setTitle("Form Window");
        stage.show();
        openStages.put("addForm", stage);

    }

    /***
     * Afiseaza fereastra de actualizare a unui profesor
     * @param service serviciul care va fi folosit de controllerul ferestrei de actualizare pentru a actualiza un profesor
     * @param selectedProfessor profesorul care va fi actualizat, datele acestuia vor fi afisate in campurile de text ale ferestrei de actualizare
     */
    public void showUpdateFormWindow(Service service, Professor selectedProfessor){
        Tuple<Scene, UpdateFormController> tuple = FXMLUtils.load("/view/updateForm.fxml");
        tuple.getSecond().setService(service);
        tuple.getSecond().init(selectedProfessor);

        if(openStages.containsKey("updateForm")){
            openStages.get("updateForm").close();
            openStages.remove("updateForm");
        }
        Stage stage = new Stage();
        stage.setScene(tuple.getFirst());
        stage.setTitle("Form Window");
        stage.show();
        openStages.put("updateForm", stage);
    }

}
