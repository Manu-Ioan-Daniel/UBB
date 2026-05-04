package controllers;

import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Materie;
import models.Professor;
import models.Student;
import services.Service;
import utils.HibernateStats;
import utils.Observer;
import utils.StageManager;

import java.util.List;
import javafx.event.ActionEvent;

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


    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;
    @FXML
    private Label pageLabel;
    @FXML
    private ComboBox<Integer> pageSizeCombo;
    @FXML
    private Button explainButton;

    @FXML
    private Label queriesLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label cacheLabel;
    @FXML
    private TextArea explainText;

    @FXML
    private CheckBox keysetToggle;

    private Service service;
    private StageManager stageManager;


    private int pageSize = 10;
    private int currentPage = 0;
    private Long lastIdCursor = null;
    private boolean useKeyset = false;

    /***
     * Initializeaza tabelele din interfata
     * Adauga acest controller ca observator al serviciului pentru a putea actualiza tabelele atunci cand datele se schimba
     */
    public void init(){
        initMateriiTable();
        initStudentsTable();
        initProfessorsTable();
        initPaginationControls();
        if (explainText != null) {
            explainText.setEditable(false);
            explainText.setText("Selecteaza o materie si apasa Explain.");
        }
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

        // load materii with notas initialized to avoid LazyInitializationException in UI
        materiiTable.setItems(FXCollections.observableList(service.getAllMateriiWithNotas()));
        materiiTable.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
           if(newVal!=null){
               // load first page of professors for selected materie
               loadProfessorsPage(newVal.getId(), 0);
               studentsTable.setItems(FXCollections.observableList(service.getAllStudentsByMaterie(newVal.getId())));
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

    private void initPaginationControls(){
        pageSizeCombo.setItems(FXCollections.observableArrayList(10,25,50,100));
        pageSizeCombo.setValue(pageSize);
        pageSizeCombo.valueProperty().addListener((obs,oldVal,newVal)->{
            pageSize = newVal;
            currentPage = 0;
            lastIdCursor = null;
            Materie sel = materiiTable.getSelectionModel().getSelectedItem();
            if(sel!=null) loadProfessorsPage(sel.getId(), 0);
        });
        // init button states
        prevButton.setDisable(true);
        nextButton.setDisable(false);

        prevButton.setOnAction(this::onPrevPage);
        nextButton.setOnAction(this::onNextPage);
        if (explainButton != null) explainButton.setOnAction(this::handleExplainCurrent);
        if (keysetToggle != null) {
            keysetToggle.selectedProperty().addListener((obs, oldV, newV) -> {
                useKeyset = newV;
                lastIdCursor = null;
                currentPage = 0;
                Materie sel = materiiTable.getSelectionModel().getSelectedItem();
                if (sel != null) loadProfessorsPage(sel.getId(), 0);
            });
        }
    }

    private void loadProfessorsPage(Long materieId, int page) {
        long t0 = System.currentTimeMillis();
        List<Professor> pageData;
        if(useKeyset) {
            pageData = service.getProfessorsPageKeyset(materieId, lastIdCursor, pageSize);
            if(!pageData.isEmpty()) {
                lastIdCursor = pageData.get(pageData.size()-1).getId();
            }
        } else {
            pageData = service.getProfessorsPageOffset(materieId, page, pageSize);
        }
        long dt = System.currentTimeMillis() - t0;
        professorsTable.setItems(FXCollections.observableList(pageData));
        currentPage = page;
        pageLabel.setText("Page " + page);
        timeLabel.setText("Last query ms: " + dt);

        try {
            var stats = HibernateStats.snapshot();
            queriesLabel.setText("Queries: " + stats.getQueryExecutionCount());
        } catch (Exception e) {
            queriesLabel.setText("Queries: N/A");
        }

        // update cache stats if SimpleEntityCache is used
        try {
            cacheLabel.setText("Cache H/M: " + service.getMaterieCacheStats());
        } catch (Exception e) {
            cacheLabel.setText("Cache H/M: N/A");
        }

        prevButton.setDisable(currentPage == 0);
        nextButton.setDisable(pageData.size() < pageSize);

        updateExplainPreview();
    }

    private void updateExplainPreview() {
        Materie sel = materiiTable.getSelectionModel().getSelectedItem();
        if (sel == null || explainText == null) {
            return;
        }
        String sql;
        if (useKeyset) {
            sql = String.format("SELECT * FROM profesori WHERE materie_id = %d AND id > %d ORDER BY id LIMIT %d", sel.getId(), lastIdCursor == null ? 0 : lastIdCursor, pageSize);
        } else {
            int offset = currentPage * pageSize;
            sql = String.format("SELECT * FROM profesori WHERE materie_id = %d ORDER BY id LIMIT %d OFFSET %d", sel.getId(), pageSize, offset);
        }
        try {
            String out = service.explainQueryToFile(sql, "reports/explain/ui_explain.txt");
            java.nio.file.Path p = java.nio.file.Paths.get(out);
            String content = java.nio.file.Files.readString(p);
            explainText.setText(content);
        } catch (Exception e) {
            explainText.setText("Explain failed: " + e.getMessage());
        }
    }

    @FXML
    public void onPrevPage(ActionEvent event){
        Materie sel = materiiTable.getSelectionModel().getSelectedItem();
        if(sel==null) return;
        if(useKeyset){
            currentPage = Math.max(0, currentPage - 1);
            lastIdCursor = null;
            for (int i = 0; i < currentPage; i++) {
                var page = service.getProfessorsPageKeyset(sel.getId(), lastIdCursor, pageSize);
                if(page.isEmpty()) break;
                lastIdCursor = page.get(page.size()-1).getId();
            }
            loadProfessorsPage(sel.getId(), currentPage);
        } else {
            int prev = Math.max(0, currentPage - 1);
            loadProfessorsPage(sel.getId(), prev);
        }
    }

    @FXML
    public void onNextPage(ActionEvent event){
        Materie sel = materiiTable.getSelectionModel().getSelectedItem();
        if(sel==null) return;
        if(useKeyset){
            loadProfessorsPage(sel.getId(), currentPage + 1);
        } else {
            loadProfessorsPage(sel.getId(), currentPage + 1);
        }
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
        materiiTable.setItems(FXCollections.observableList(service.getAllMateriiWithNotas()));
            if(materiiTable.getSelectionModel().getSelectedItem()!=null){
                studentsTable.setItems(FXCollections.observableList(service.getAllStudentsByMaterie(materiiTable.getSelectionModel().getSelectedItem().getId())));
                loadProfessorsPage(materiiTable.getSelectionModel().getSelectedItem().getId(), 0);
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
            loadProfessorsPage(selectedMaterie.getId(), currentPage);
        }
    }

    @FXML
    public void handleExplainCurrent(ActionEvent event) {
        updateExplainPreview();
    }

}
