package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.IMainService;

public class ReservationFormController {

    private Long rideId;
    private IMainService IMainService;

    @FXML
    private TextField clientNameField;

    @FXML
    private TextField noSeatsField;


    @FXML
    public void handleConfirm(){
        if(clientNameField.getText().isEmpty() || noSeatsField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setHeaderText("Please fill in all fields");
            alert.showAndWait();
            return;
        }
        int noSeats = Integer.parseInt(noSeatsField.getText());
        IMainService.makeReservation(rideId, clientNameField.getText(), noSeats);
        ((Stage)clientNameField.getScene().getWindow()).close();

    }

    public void init(IMainService IMainService, Long rideId) {
        this.IMainService = IMainService;
        this.rideId = rideId;
    }

}
