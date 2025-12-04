package Ui;

import domain.Duck;
import domain.Person;
import enums.DuckType;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import observer.Observer;
import repo.DatabaseUserRepository;
import service.ServiceUser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class UiJavaFx extends Application implements Observer {
    private TableView<Duck> tableDucks;
    private TableView<Person> tablePersons;
    private ServiceUser serviceUser;
    private ComboBox<String> filterBox;
    private Pagination ducksPagination;
    private static final int DUCKS_PER_PAGE = 3;
    private Pagination personsPagination;
    private static final int PERSONS_PER_PAGE = 1;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        String url = "jdbc:postgresql://localhost:5432/MAP?user=postgres&password=123";
        DatabaseUserRepository userRepo = new DatabaseUserRepository(url);
        serviceUser = new ServiceUser(userRepo);
        serviceUser.addObserver(this);
        initializeDucksTable();
        initializeFilterBox();
        initializePersonsTable();
        initializeDucksPagination();
        initializePersonsPagination();
        VBox ducksBox = new VBox(tableDucks,ducksPagination);
        VBox personsBox = new VBox(tablePersons,personsPagination);
        personsBox.setPrefWidth(700);
        ducksBox.setPrefWidth(600);
        HBox tablesBox = new HBox(ducksBox, personsBox);
        root.setCenter(tablesBox);
        root.setBottom(filterBox);
        HBox buttonsBar = createButtonsBar();
        root.setTop(buttonsBar);
        Scene scene = new Scene(root, 1300, 400);
        stage.setScene(scene);
        stage.setTitle("All Ducks Table");
        stage.show();
    }

    private void initializeDucksTable() {
        tableDucks = new TableView<>();
        TableColumn<Duck, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Duck, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Duck, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Duck, String> friendsCol = new TableColumn<>("Friends");
        friendsCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFriends().toString()));

        TableColumn<Duck, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Duck, Double> speedCol = new TableColumn<>("Speed");
        speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));

        TableColumn<Duck, Double> rezistanceCol = new TableColumn<>("Rezistance");
        rezistanceCol.setCellValueFactory(new PropertyValueFactory<>("rezistance"));

        tableDucks.getColumns().addAll(Arrays.asList(idCol, usernameCol, emailCol, friendsCol, typeCol, speedCol, rezistanceCol));
    }

    private void initializePersonsTable() {
        tablePersons = new TableView<>();
        TableColumn<Person, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Person, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Person, String> nameCol = new TableColumn<>("Nume");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Person, String> surnameCol = new TableColumn<>("Prenume");
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Person, String> occupationCol = new TableColumn<>("Ocupație");
        occupationCol.setCellValueFactory(new PropertyValueFactory<>("occupation"));

        TableColumn<Person, String> dobCol = new TableColumn<>("Data nașterii");
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        TableColumn<Person, Integer> empathyCol = new TableColumn<>("Empatie");
        empathyCol.setCellValueFactory(new PropertyValueFactory<>("empathyScore"));

        TableColumn<Person, String> friendsCol = new TableColumn<>("Friends");
        friendsCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFriends().toString()));

        tablePersons.getColumns().addAll(Arrays.asList(idCol, usernameCol, emailCol, nameCol, surnameCol, occupationCol, dobCol, empathyCol, friendsCol));
    }

    private void initializeFilterBox() {
        filterBox = new ComboBox<>();
        filterBox.getItems().addAll("NONE", "SWIMMING", "FLYING");
        filterBox.setValue("NONE");
        filterBox.setOnAction(e -> {
            initializeDucksPagination();
            ducksPagination.setCurrentPageIndex(0);
        });
    }
    private void initializeDucksPagination(){
        if(ducksPagination==null) {
            ducksPagination = new Pagination();
        }
        ducksPagination.setPageCount((serviceUser.duckCount(getSelectedDuckType())+DUCKS_PER_PAGE-1)/ DUCKS_PER_PAGE);
        ducksPagination.setPageFactory(this::getDucksPage);
    }
    private Node getDucksPage(int pageIndex){
        List<Duck> ducks = serviceUser.getDucksPage(pageIndex, DUCKS_PER_PAGE, getSelectedDuckType());
        tableDucks.getItems().setAll(ducks);
        return new VBox();
    }
    private void initializePersonsPagination(){
        if(personsPagination==null) {
            personsPagination = new Pagination();
        }
        personsPagination.setPageCount(serviceUser.personCount());
        personsPagination.setPageFactory(this::getPersonsPage);
    }
    private Node getPersonsPage(int pageIndex){
        List<Person> persons=serviceUser.getPersonsPage(pageIndex,PERSONS_PER_PAGE);
        tablePersons.getItems().setAll(persons);
        return new VBox();
    }
    private DuckType getSelectedDuckType(){
        String selected = filterBox.getValue();
        if(selected.equals("NONE")){
            return null;
        }
        return DuckType.valueOf(selected);
    }
    private HBox createButtonsBar() {
        Button addUserBtn = new Button("Adauga utilizator");
        addUserBtn.setOnAction(e -> addUserFx());

        Button removeUserBtn = new Button("Sterge utilizator");
        removeUserBtn.setOnAction(e -> removeUserFx());

        Button addFriendBtn = new Button("Adauga prietenie");
        addFriendBtn.setOnAction(e -> addFriendFx());

        Button removeFriendBtn = new Button("Sterge prietenie");
        removeFriendBtn.setOnAction(e -> removeFriendFx());

        Button communitiesBtn = new Button("Numar comunități");
        communitiesBtn.setOnAction(e -> showNumberOfCommunitiesFx());

        Button largestBtn = new Button("Cea mai mare comunitate");
        largestBtn.setOnAction(e -> showLargestCommunityFx());

        HBox h = new HBox(10, addUserBtn, removeUserBtn, addFriendBtn, removeFriendBtn, communitiesBtn, largestBtn);
        h.setPadding(new Insets(10));
        h.setAlignment(Pos.CENTER);
        return h;
    }



    @Override
    public void update() {
        initializeDucksPagination();
        initializePersonsPagination();
    }

    private String input(String text) {
        TextInputDialog d = new TextInputDialog();
        d.setHeaderText(text);
        return d.showAndWait().orElse("");
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(msg);
        a.showAndWait();
    }

    private void addUserFx() {
        try {
            Long id = Long.parseLong(input("ID:"));
            String username = input("Username:");
            String email = input("Email:");
            String password = input("Parola:");
            String typeStr = input("Tip utilizator (duck/person):").trim().toLowerCase();

            if (typeStr.equals("duck")) {
                DuckType dt = DuckType.valueOf(input("Tip rata (FLYING/SWIMMING/FLYING_AND_SWIMMING):").toUpperCase());
                double speed = Double.parseDouble(input("Viteza:"));
                double rez = Double.parseDouble(input("Rezistenta:"));

                Duck d = new Duck(id, username, email, password, dt, speed, rez);
                serviceUser.addUser(d);

            } else {
                String name = input("Nume:");
                String surname = input("Prenume:");
                String dob = input("Data nașterii:");
                String occupation = input("Ocupație:");
                int empathy = Integer.parseInt(input("Empatie (0-100):"));

                Person p = new Person(id, username, email, password, name, surname, occupation, dob, empathy);
                serviceUser.addUser(p);
            }

        } catch (Exception e) {
            alert("Eroare: " + e.getMessage());
        }
    }

    private void removeUserFx() {
        try {
            long id = Long.parseLong(input("ID utilizator de șters:"));
            serviceUser.removeUser(id);
        } catch (Exception e) {
            alert("Eroare: " + e.getMessage());
        }
    }

    private void addFriendFx() {
        try {
            long id1 = Long.parseLong(input("ID utilizator:"));
            long id2 = Long.parseLong(input("ID prieten:"));
            serviceUser.addFriend(id1, id2);
        } catch (Exception e) {
            alert("Eroare: " + e.getMessage());
        }
    }

    private void removeFriendFx() {
        try {
            long id1 = Long.parseLong(input("ID utilizator:"));
            long id2 = Long.parseLong(input("ID prieten:"));
            serviceUser.removeFriend(id1, id2);
        } catch (Exception e) {
            alert("Eroare: " + e.getMessage());
        }
    }

    private void showNumberOfCommunitiesFx() {
        try {
            int nr = serviceUser.getNumberOfCommunities();
            alert("Număr comunități: " + nr);
        } catch (Exception e) {
            alert("Eroare: " + e.getMessage());
        }
    }

    private void showLargestCommunityFx() {
        try {
            int size = serviceUser.getBiggestCommunitySize();
            alert("Cea mai mare comunitate are: " + size + " membri");
        } catch (Exception e) {
            alert("Eroare: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
