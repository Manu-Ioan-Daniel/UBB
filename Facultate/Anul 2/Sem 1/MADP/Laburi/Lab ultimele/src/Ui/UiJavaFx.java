package Ui;
import domain.Duck;
import domain.Person;
import domain.User;
import enums.DuckType;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
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
        friendsCol.setCellValueFactory(cell -> new SimpleStringProperty(serviceUser.getFriendNames(cell.getValue().getFriends())));

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
        friendsCol.setCellValueFactory(cell -> new SimpleStringProperty(serviceUser.getFriendNames(cell.getValue().getFriends())));

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
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Adaugă utilizator");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setPrefHeight(500);
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        TextField username = new TextField();
        TextField email = new TextField();
        TextField password = new TextField();

        ComboBox<String> userType = new ComboBox<>();
        userType.getItems().addAll("duck", "person");
        userType.setValue("duck");
        ComboBox<String> duckType = new ComboBox<>();
        duckType.getItems().addAll("FLYING", "SWIMMING", "FLYING_AND_SWIMMING");

        TextField speed = new TextField();
        TextField rez = new TextField();

        TextField name = new TextField();
        TextField surname = new TextField();
        TextField occupation = new TextField();
        TextField dob = new TextField();
        TextField empathy = new TextField();

        int r = 0;
        grid.add(new Label("Username:"), 2, r); grid.add(username, 3, r); r++;

        grid.add(new Label("Email:"), 0, r); grid.add(email, 1, r);
        grid.add(new Label("Parola:"), 2, r); grid.add(password, 3, r); r++;
        grid.add(new Label("Tip utilizator:"), 0, r);
        grid.add(userType, 1, r);
        r++;

        VBox duckBox = new VBox(8);
        duckBox.getChildren().addAll(
                new Label("DuckType:"), duckType,
                new Label("Viteză:"), speed,
                new Label("Rezistență:"), rez
        );
        duckBox.setVisible(true);
        duckBox.setManaged(true);
        grid.add(duckBox, 0, r, 4, 1);
        r++;

        VBox personBox = new VBox(8);
        personBox.getChildren().addAll(
                new Label("Nume:"), name,
                new Label("Prenume:"), surname,
                new Label("Ocupație:"), occupation,
                new Label("Data nașterii:"), dob,
                new Label("Empatie:"), empathy
        );
        personBox.setVisible(false);
        personBox.setManaged(false);
        grid.add(personBox, 0, r, 4, 1);

        userType.setOnAction(e -> {
            boolean isDuck = userType.getValue() != null && userType.getValue().equals("duck");

            duckBox.setVisible(isDuck);
            duckBox.setManaged(isDuck);

            personBox.setVisible(!isDuck);
            personBox.setManaged(!isDuck);
        });

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                try {
                    String type = userType.getValue();
                    if (type.equals("duck")) {
                        Duck d = new Duck(
                                username.getText(),
                                email.getText(),
                                password.getText(),
                                DuckType.valueOf(duckType.getValue()),
                                Double.parseDouble(speed.getText()),
                                Double.parseDouble(rez.getText())
                        );
                        serviceUser.addUser(d);
                    } else {
                        Person p = new Person(
                                username.getText(),
                                email.getText(),
                                password.getText(),
                                name.getText(),
                                surname.getText(),
                                occupation.getText(),
                                dob.getText(),
                                Integer.parseInt(empathy.getText())
                        );
                        serviceUser.addUser(p);
                    }
                } catch (Exception e) {
                    alert("Eroare: " + e.getMessage());
                }
            }
        });
    }

    private void removeUserFx() {
        try {
            String nume= input("Introdu username-ul utilizatorului de sters:");
            serviceUser.removeUser(nume);
            alert("Utilizator șters!");
        } catch (Exception e) {
            alert("Eroare: " + e.getMessage());
        }
    }

    private void addFriendFx() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Adaugă prietenie");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        TextField userIdField = new TextField();
        TextField friendIdField = new TextField();

        grid.add(new Label("Nume utilizator:"), 0, 0);
        grid.add(userIdField, 1, 0);
        grid.add(new Label("Nume prieten:"), 0, 1);
        grid.add(friendIdField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                try {
                    String username= userIdField.getText();
                    String friendUsername= friendIdField.getText();
                    serviceUser.addFriend(username,friendUsername);
                    serviceUser.addFriend(friendUsername,username);
                    alert("Prietenie adaugata!");
                } catch (Exception e) {
                    alert("Eroare: " + e.getMessage());
                }
            }
        });
    }

    private void removeFriendFx() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Șterge prietenie");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        TextField usernameField = new TextField();
        TextField friendUsernameField = new TextField();

        grid.add(new Label("Nume utilizator:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Nume prieten:"), 0, 1);
        grid.add(friendUsernameField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                try {
                    String username = usernameField.getText();
                    String friendUsername = friendUsernameField.getText();
                    serviceUser.removeFriend(username, friendUsername);
                    serviceUser.removeFriend(friendUsername, username);
                    alert("Prietenie ștearsă!");
                } catch (Exception e) {
                    alert("Eroare: " + e.getMessage());
                }
            }
        });
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
            List<User> community = serviceUser.getBiggestCommunity();
            StringBuilder users= new StringBuilder();
            for(User u:community){
                users.append(u.getId()).append(" ").append(u.getUsername()).append("\n");
            }
            alert(users.toString());
        } catch (Exception e) {
            alert("Eroare: " + e.getMessage());
        }
    }

    public void launchApp() {
        launch();
    }
}



