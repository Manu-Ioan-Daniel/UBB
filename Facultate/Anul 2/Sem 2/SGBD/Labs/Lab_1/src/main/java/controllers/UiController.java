package controllers;

import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Materie;
import models.Professor;
import models.Student;
import services.Service;
import utils.Observer;
import utils.StageManager;

public class UiController implements Observer {

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
    private StageManager stageManager;

    /***
     * Initializeaza tabelele din interfata
     * Adauga acest controller ca observator al serviciului pentru a putea actualiza tabelele atunci cand datele se schimba
     */
    public void init(){
        initMateriiTable();
        initStudentsTable();
        initProfessorsTable();
        service.addObserver(this);
    }

    public void setService(Service service){
        this.service = service;
    }

    public void setStageManager(StageManager stageManager){
        this.stageManager = stageManager;
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

    /***
     * Afiseaza fereastra de adaugare a unui profesor pentru materia selectata in tabelul de materii
     * Daca nu este selectata nicio materie afiseaza un mesaj de eroare
     */
    @FXML
    private void handleAdd(){
        Materie materie = materiiTable.getSelectionModel().getSelectedItem();
        if(materie!=null)
            stageManager.showAddFormWindow(service,materie.getId());
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Alege o materie");
            alert.setContentText("Nu ai ales nici o materie");
            alert.showAndWait();
        }
    }

    /***
     * Sterge profesorul selectat in tabelul de profesori
     * Daca nu este selectat niciun profesor afiseaza un mesaj de eroare
     */
    @FXML
    private void handleDelete(){
        Professor selectedProfessor = professorsTable.getSelectionModel().getSelectedItem();
        if(selectedProfessor!=null)
            service.deleteProfessor(selectedProfessor.getId());
        else{
            Alert  alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Alege un profesor");
            alert.setContentText("Nu ai ales nici un profesor");
            alert.showAndWait();
        }
    }

    /***
     * Afiseaza fereastra de actualizare a profesorului selectat in tabelul de profesori
     * Daca nu este selectat niciun profesor afiseaza un mesaj de eroare
     */
    @FXML
    private void handleUpdate(){
        if(professorsTable.getSelectionModel().getSelectedItem()!=null)
            stageManager.showUpdateFormWindow(service,professorsTable.getSelectionModel().getSelectedItem());
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Alege un profesor");
            alert.setContentText("Nu ai ales nici un profesor");
            alert.showAndWait();
        }
    }

    /***
     * Reincarca datele din tabele
     * Daca este selectata o materie, reincarca si tabelele de studenti si profesori pentru materia selectata
     */
    @FXML
    public void handleRefresh(){
        materiiTable.setItems(FXCollections.observableList(service.getAllMaterii()));
            if(materiiTable.getSelectionModel().getSelectedItem()!=null){
                studentsTable.setItems(FXCollections.observableList(service.getAllStudentsByMaterie(materiiTable.getSelectionModel().getSelectedItem().getId())));
                professorsTable.setItems(FXCollections.observableList(service.getAllProfessorsByMaterie(materiiTable.getSelectionModel().getSelectedItem().getId())));
            }
    }

    /***
     * Reincarca datele din tabelul de materii
     * Daca este selectata o materie, reincarca si tabelele de studenti si profesori pentru materia selectata
     * Functie apelata automat cand service ul anunta ca datele s au modificat
     */
    @Override
    public void update() {
        Materie selectedMaterie = materiiTable.getSelectionModel().getSelectedItem();
        if (selectedMaterie != null) {
            professorsTable.setItems(
                    FXCollections.observableList(
                            service.getAllProfessorsByMaterie(selectedMaterie.getId())
                    )
            );
        }
    }


}
