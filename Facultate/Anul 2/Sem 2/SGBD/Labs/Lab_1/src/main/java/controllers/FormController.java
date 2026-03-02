package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import services.Service;
import utils.TextFieldUtils;

public class FormController {

    @FXML
    private TextField ageField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField materieField;

    @FXML
    private TextField nameField;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    public void init(){
        TextFieldUtils.makeNumericField(ageField);
        TextFieldUtils.makeNumericField(materieField);
    }

    public void handleSubmit(){
        service.addProfessor(nameField.getText(), ageField.getText(), emailField.getText(), materieField.getText());
    }


}