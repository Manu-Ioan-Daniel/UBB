package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ModifyController {

    @FXML
    private TableColumn<?, ?> clientColumn;

    @FXML
    private TableColumn<?, ?> courseColumn;

    @FXML
    private TableColumn<?, ?> noSeatsColumn;

    @FXML
    private TableView<?> reservationsTable;

    @FXML
    void handleCancelReservation(ActionEvent event) {

    }

}
