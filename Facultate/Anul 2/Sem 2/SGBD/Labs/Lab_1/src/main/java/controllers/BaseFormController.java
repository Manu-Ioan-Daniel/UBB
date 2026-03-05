package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.Service;
import utils.TextFieldUtils;

public class BaseFormController {

    @FXML
    protected VBox root;

    @FXML
    protected TextField ageField;

    @FXML
    protected TextField emailField;

    @FXML
    protected TextField materieField;

    @FXML
    protected TextField nameField;

    protected Service service;

    public void setService(Service service) {
        this.service = service;
    }

    /***
     * Initializeaza campurile de text ale formularului pentru a accepta doar cifre acolo unde este cazul
     */
    public void init(){
        TextFieldUtils.makeNumericField(ageField);
        TextFieldUtils.makeNumericField(materieField);
    }

    /***
     * @return stage ul ferestrei curente
     */
    protected Stage getStage(){
        return (Stage)root.getScene().getWindow();
    }

}