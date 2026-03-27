package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SearchFormController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField departureHourField;

    @FXML
    private TextField destinationField;

    @FXML
    private TableColumn<?, ?> noSeatColumn;

    @FXML
    private TableColumn<?, ?> reservedByColumn;

    @FXML
    private TableView<?> seatsTable;

    @FXML
    void handleSearch(ActionEvent event) {

    }

}
