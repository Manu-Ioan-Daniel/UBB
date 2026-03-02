package controllers;

import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Materie;
import models.Professor;
import models.Student;
import services.Service;
import utils.StageManager;

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
    private TableColumn<Student, Integer> studAgeColumn;

    @FXML
    private TableColumn<Student, String> studNameColumn;

    @FXML
    private TableColumn<Student,Long> studNotaColumn;

    @FXML
    private TableView<Materie> materiiTable;

    @FXML
    private TableView<Professor> professorsTable;

    @FXML
    private TableView<Student> studentsTable;

    private Service service;

    public void init(){
        initMateriiTable();
        initStudentsTable();
        initProfessorsTable();
    }

    public void setService(Service service){
        this.service = service;
    }

    private void initMateriiTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));

        materiiTable.setItems(FXCollections.observableList(service.getAllMaterii()));
        materiiTable.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
           if(newVal!=null){
               studentsTable.setItems(FXCollections.observableList(service.getAllStudentsByMaterie(newVal.getId())));
               professorsTable.setItems(FXCollections.observableList(service.getAllProfessorsByMaterie(newVal.getId())));
           }
        });
    }

    private void initStudentsTable(){
        studNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        studAgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        studNotaColumn.setCellValueFactory(cell->{
            if(materiiTable.getSelectionModel().getSelectedItem()!=null){
                Materie materie = materiiTable.getSelectionModel().getSelectedItem();
                Student student = cell.getValue();
                if(service.getNota(materie.getId(), student.getId()) == null){
                    return null;
                }
                return new SimpleLongProperty(service.getNota(materie.getId(),student.getId())).asObject();
            }
            else{
                return null;
            }
        });
    }

    private void initProfessorsTable(){
        profNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        profEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        profAgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
    }

    @FXML
    private void handleAdd(){
        StageManager.showFormWindow(service);
    }

    @FXML
    private void handleDelete(){

    }

    @FXML
    private void handleUpdate(){

    }
}
