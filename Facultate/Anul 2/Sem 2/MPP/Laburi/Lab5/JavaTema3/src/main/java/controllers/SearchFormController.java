package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.MainService;
import dtos.RideDTO;

public class SearchFormController {

    private MainService mainService;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField departureHourField;

    @FXML
    private TextField destinationField;

    @FXML
    private TableColumn<RideDTO, Integer> noSeatColumn;

    @FXML
    private TableColumn<RideDTO, String> reservedByColumn;

    @FXML
    private TableView<RideDTO> seatsTable;

    @FXML
    void handleSearch() {
        if(datePicker.getValue() == null || departureHourField.getText().isEmpty() || destinationField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }
        try {
            seatsTable.setItems(FXCollections.observableList(mainService.findRides(datePicker.getValue(), departureHourField.getText(), destinationField.getText())));
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Search failed");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void init(MainService mainService) {
        this.mainService = mainService;
        initTable();
    }

    public void initTable(){
        noSeatColumn.setCellValueFactory(new PropertyValueFactory<>("noSeats"));
        reservedByColumn.setCellValueFactory(new PropertyValueFactory<>("reservedBy"));
    }
}
