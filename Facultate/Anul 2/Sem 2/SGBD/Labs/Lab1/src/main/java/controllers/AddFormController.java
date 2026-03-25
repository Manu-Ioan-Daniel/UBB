package controllers;

import javafx.scene.control.Alert;

public class AddFormController extends BaseFormController {

    /***
     * Adauga un profesor in baza de date folosind datele din campurile de text
     * Daca datele nu sunt valide, altfel afiseaza un mesaj de eroare
     */
    public void handleSubmit(){
        try {
            service.addProfessor(nameField.getText(), ageField.getText(), emailField.getText(), materieField.getText());
            getStage().close();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /***
     * Initializeaza campurile de text ale ferestrei de adaugare cu datele necesare pentru adaugarea unui profesor
     * @param materieId id ul materiei la care adaugam profesorul, nu este editabil, doar afisat intr un textfield
     */
    public void init(Long materieId){
        super.init();
        materieField.setText(materieId.toString());
    }
}
