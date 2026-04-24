package controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Ride;
import services.IMainService;
import utils.Observer;
import utils.StageManager;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class MainController implements Observer {

    private IMainService mainService;

    @FXML
    private TableView<Ride> coursesTable;

    @FXML
    private TableColumn<Ride, LocalDateTime> dateColumn;

    @FXML
    private TableColumn<Ride, String> destinationColumn;

    @FXML
    private TableColumn<Ride, Integer> noSeatsColumn;


    @FXML
    void handleSearch(ActionEvent event) {

        StageManager.openSearchForm(mainService);
    }

    @FXML
    void handleModify(ActionEvent event) {

        StageManager.openModifyWindow(mainService);
    }

    @FXML
    void handleReservation(ActionEvent event) {
        Ride ride = coursesTable.getSelectionModel().getSelectedItem();
        if(ride == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No selection");
            alert.setHeaderText("No ride selected");
            alert.setContentText("Please select a ride to make a reservation");
            alert.showAndWait();
            return;
        }
        StageManager.openReservationForm(mainService, ride.getId());
    }

    @FXML
    void handleLogout(ActionEvent event) {
        ((javafx.stage.Stage) coursesTable.getScene().getWindow()).close();
    }

    public void init(IMainService mainService) {
        this.mainService = mainService;
        mainService.addObserver(this);
        initTable();
    }

    private void initTable(){
        dateColumn.setCellValueFactory(cell->{
            LocalTime departureTime = cell.getValue().getDepartureTime();
            LocalDateTime dateTime = LocalDateTime.of(cell.getValue().getDate(), departureTime);
            return new SimpleObjectProperty<>(dateTime);
        });
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        noSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        coursesTable.setItems(FXCollections.observableList(mainService.getAllRides()));
    }

    private void loadTable(){
        coursesTable.setItems(FXCollections.observableList(mainService.getAllRides()));
    }


    @Override
    public void update() {
        Platform.runLater(this::loadTable);
    }
}
