package controller;

import domain.Duck;
import domain.Person;
import domain.User;
import enums.DuckType;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.UserModel;
import utils.alerts.Alert;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddUserFormController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private DatePicker date;

    @FXML
    private VBox duckFieldsVBox;

    @FXML
    private ComboBox<DuckType> duckTypeComboBox;

    @FXML
    private TextField emailField;

    @FXML
    private Slider empathySlider;

    @FXML
    private TextField name;

    @FXML
    private TextField occupation;

    @FXML
    private PasswordField passwordField;

    @FXML
    private VBox personFieldsVBox;

    @FXML
    private TextField resistance;

    @FXML
    private TextField speed;

    @FXML
    private TextField surname;

    @FXML
    private ComboBox<String> typeComboBox;


    @FXML
    private TextField usernameField;

    private double offsetX = 0, offsetY = 0;
    UserModel userModel;
    private Stage errorAlertStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        duckFieldsVBox.setVisible(false);
        personFieldsVBox.setVisible(true);
        date.setValue(LocalDate.now());
        root.setOnMousePressed(event -> {
            offsetX = event.getSceneX();
            offsetY = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(event.getScreenX() - offsetX);
            stage.setY(event.getScreenY() - offsetY);
        });
        initTypeComboBox();
        initDuckTypeComboBox();

    }

    private void initTypeComboBox() {
        typeComboBox.getItems().add("Duck");
        typeComboBox.getItems().add("Person");
        typeComboBox.setValue("Person");
        typeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean isDuck = "Duck".equals(newVal);
            personFieldsVBox.setVisible(!isDuck);
            duckFieldsVBox.setVisible(isDuck);
        });
    }

    private void initDuckTypeComboBox() {
        duckTypeComboBox.getItems().add(DuckType.FLYING);
        duckTypeComboBox.getItems().add(DuckType.SWIMMING);
        duckTypeComboBox.setValue(DuckType.FLYING);
    }

    @FXML
    public void handleAdd(ActionEvent e) {
        boolean valid = checkInputs(usernameField,emailField,passwordField);
        User user;
        if(typeComboBox.getValue().equals("Duck")) {
            valid &= checkInputs(speed,resistance);
        }else{
            valid &=checkInputs(name,surname,occupation);
        }
        if(!valid){
            return;
        }

        if(typeComboBox.getValue().equals("Duck")) {
            user = new Duck(
                    usernameField.getText(), emailField.getText(),passwordField.getText(),duckTypeComboBox.getValue(),
                    Double.parseDouble(speed.getText()),Double.parseDouble(resistance.getText()));
        }else{
            user = new Person(usernameField.getText(), emailField.getText(),passwordField.getText(),name.getText(),surname.getText(),
                    date.getValue(),occupation.getText(),(int)empathySlider.getValue());
        }
        try {
            userModel.addUser(user);
            ((Stage)root.getScene().getWindow()).close();
        }catch(Exception ex){
            if(errorAlertStage!=null){
                errorAlertStage.close();
            }
            errorAlertStage = Alert.errorAlert(ex.getMessage());
            errorAlertStage.setOnHidden(event -> {
                errorAlertStage = null;
            });
            errorAlertStage.show();
        }
    }

    private boolean checkInputs(TextField... fields) {
        boolean valid=true;
        for(TextField field : fields) {
            if(field.getText().isEmpty()){
                showError(field);
                valid=false;
            }
        }
        return valid;
    }

    private void showError(TextField field) {
        if (!field.getStyleClass().contains("text-field-error")) {
            field.getStyleClass().add("text-field-error");
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            field.getStyleClass().remove("text-field-error");
        });
        pause.play();
    }

    @FXML
    public void handleClose(ActionEvent e) {
        ((Stage) root.getScene().getWindow()).close();
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}

