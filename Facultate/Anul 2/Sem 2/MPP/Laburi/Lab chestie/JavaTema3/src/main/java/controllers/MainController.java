package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {

    @FXML
    private TableView<?> coursesTable;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private TableColumn<?, ?> destinationColumn;

    @FXML
    private TableColumn<?, ?> noSeatsColumn;

    @FXML
    void handleSearch(ActionEvent event) {

    }

}
