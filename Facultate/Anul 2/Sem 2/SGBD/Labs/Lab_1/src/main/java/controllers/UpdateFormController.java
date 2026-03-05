package controllers;

import javafx.scene.control.Alert;
import models.Professor;

public class UpdateFormController extends BaseFormController {

    private Professor selectedProfessor;

    /***
     * Initializeaza campurile formularului cu datele profesorului selectat
     * @param selectedProfessor profesorul selectat pentru editare
     */
    public void init(Professor selectedProfessor) {
        super.init();
        this.selectedProfessor = selectedProfessor;
        initTextFields();
    }

    /***
     * Initializeaza campurile de text ale formularului cu datele profesorului selectat
     */
    public void initTextFields(){
        nameField.setText(selectedProfessor.getName());
        ageField.setText(selectedProfessor.getAge().toString());
        emailField.setText(selectedProfessor.getEmail());
        materieField.setText(selectedProfessor.getMaterieId().toString());
    }

    /***
     * Actualizeaza profesorul selectat in baza de date folosind datele din campurile de text
     * Daca datele nu sunt valide afiseaza un mesaj de eroare
     */
    public void handleSubmit(){
        try {
            service.updateProfessor(selectedProfessor.getId(), nameField.getText(), ageField.getText(), emailField.getText(), materieField.getText());
            getStage().close();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
