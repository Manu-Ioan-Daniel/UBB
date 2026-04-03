package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.MainService;

public class ReservationFormController {

    private Long rideId;
    private MainService mainService;

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
        mainService.makeReservation(rideId, clientNameField.getText(), noSeats);

    }

    public void init(MainService mainService, Long rideId) {
        this.mainService = mainService;
        this.rideId = rideId;
    }

}
