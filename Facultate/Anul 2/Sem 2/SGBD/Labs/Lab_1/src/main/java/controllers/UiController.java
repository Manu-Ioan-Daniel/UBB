package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Materie;
import models.Professor;
import models.Student;
import services.Service;

public class UiController {

    @FXML
    private TableColumn<Materie, Integer> creditColumn;

    @FXML
    private TableColumn<Materie, Long> idColumn;

    @FXML
    private TableColumn<Materie, String> nameColumn;

    @FXML
    private TableColumn<Professor, Integer> profAgeColumn;

    @FXML
    private TableColumn<Professor, String> profEmailColumn;

    @FXML
    private TableColumn<Professor, String> profNameColumn;

    @FXML
    private TableColumn<Student, String> studEmailColumn;

    @FXML
    private TableColumn<Student, String> studNameColumn;

    @FXML
    private TableView<Materie> materiiTable;

    @FXML
    private TableView<Professor> professorsTable;

    @FXML
    private TableView<Student> studentsTable;

    private Service service;

    private void init(){

    }

    private void setService(Service service){
        this.service = service;
    }

    private void initMateriiTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        materiiTable.setItems
    }
}
