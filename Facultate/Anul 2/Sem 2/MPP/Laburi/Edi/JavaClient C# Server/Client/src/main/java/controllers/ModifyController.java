package controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Reservation;
import services.IMainService;
import utils.Observer;

public class ModifyController implements Observer {

    private IMainService mainService;

    @FXML
    private TableColumn<Reservation, String> clientColumn;

    @FXML
    private TableColumn<Reservation, String> courseColumn;

    @FXML
    private TableColumn<Reservation, Integer> noSeatsColumn;

    @FXML
    private TableView<Reservation> reservationsTable;

    @FXML
    void handleCancelReservation(ActionEvent event) {
        Reservation reservation = reservationsTable.getSelectionModel().getSelectedItem();
        if(reservation == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No selection");
            alert.setHeaderText("No reservation selected");
            alert.setContentText("Please select a reservation to cancel");
            alert.showAndWait();
            return;
        }
        mainService.cancelReservation(reservation.getId());
        ((Stage)reservationsTable.getScene().getWindow()).close();
    }

    public void init(IMainService mainService) {
        this.mainService = mainService;
        mainService.addObserver(this);
        initTable();
    }

    private void initTable() {
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        courseColumn.setCellValueFactory(cell->{
            Reservation reservation = cell.getValue();
            return new SimpleStringProperty(mainService.getRide(reservation.getRideId()).getDestination());
        });
        noSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("reservedSeatsCount"));
        reservationsTable.setItems(FXCollections.observableList(mainService.findReservations()));
    }

    private void loadTable(){
        reservationsTable.setItems(FXCollections.observableList(mainService.findReservations()));
    }

    @Override
    public void update() {
        Platform.runLater(this::loadTable);
    }
}
