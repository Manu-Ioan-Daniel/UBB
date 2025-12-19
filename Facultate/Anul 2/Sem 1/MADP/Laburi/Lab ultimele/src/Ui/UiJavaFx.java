package Ui;
import domain.Duck;
import domain.Person;
import domain.User;
import enums.DuckType;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import observer.Observer;
import service.ServiceFriendRequest;
import service.ServiceMessage;
import service.ServiceUser;
import utils.InformationAlert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UiJavaFx  implements Observer {
    private final User currentUser;

    private static final int DUCKS_PER_PAGE = 3;
    private static final int PERSONS_PER_PAGE = 1;

    private final ServiceUser serviceUser;
    private final ServiceMessage serviceMessage;
    private final ServiceFriendRequest serviceFriendRequest;

    private TableView<Duck> tableDucks;
    private TableView<Person> tablePersons;

    private Pagination ducksPagination;
    private Pagination personsPagination;

    private ComboBox<String> filterBox;

    public UiJavaFx(ServiceUser service, User currentUser, ServiceMessage serviceMessage, ServiceFriendRequest serviceFriendRequest){
        this.serviceUser = service;
        this.currentUser = currentUser;
        this.serviceUser.addObserver(this);
        this.serviceMessage = serviceMessage;
        this.serviceFriendRequest = serviceFriendRequest;
        this.serviceFriendRequest.addObserver(this);
    }
    public void show(Stage stage) {
        BorderPane root = new BorderPane();
        init();
        initLayout(root);
        Scene scene = new Scene(root, 1300, 400);
        stage.setScene(scene);
        stage.setTitle("All Ducks Table");
        stage.show();
        initNotifications();

    }



    /* ---------------------- INITIALIZATION ---------------------- */
    private void initNotifications() {
        refreshFriendRequestNotifications();
    }

    private void refreshFriendRequestNotifications() {
        String notification = serviceFriendRequest.getUserFriendRequests(currentUser.getId()).stream()
                .filter(fr -> "pending".equals(fr.getStatus()))
                .map(fr -> "You have a friend request from: "
                        + serviceUser.getUserById(fr.getFrom()).getUsername())
                .collect(Collectors.joining("\n"));

        if (!notification.isEmpty()) {
            InformationAlert.alert(notification);
        }
    }


    private void init(){
        initFilter();
        initTables();
        initPagination();
    }
    private void initTables() {
        initDucksTable();
        initPersonsTable();
    }

    private void initPagination() {
        initDucksPagination();
        initPersonsPagination();
    }

    private void initFilter() {
        filterBox = new ComboBox<>();
        filterBox.getItems().addAll("NONE", "SWIMMING", "FLYING");
        filterBox.setValue("NONE");

        filterBox.setOnAction(e -> {
            initDucksPagination();
            ducksPagination.setCurrentPageIndex(0);
        });
    }

    private void initLayout(BorderPane root) {
        VBox ducksBox = new VBox(tableDucks, ducksPagination);
        VBox personsBox = new VBox(tablePersons, personsPagination);

        ducksBox.setPrefWidth(600);
        personsBox.setPrefWidth(700);

        HBox tablesBox = new HBox(ducksBox, personsBox);

        Label loggedInLabel = new Label("Logged in as: " + currentUser.getUsername());
        loggedInLabel.setPadding(new Insets(5));

        VBox topBox = new VBox(loggedInLabel, filterBox);

        root.setCenter(tablesBox);
        root.setTop(topBox);
        root.setBottom(createButtonsBar());
    }



    private void initDucksTable() {
        tableDucks = new TableView<>();

        TableColumn<Duck, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Duck, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Duck, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Duck, String> friendsCol = new TableColumn<>("Friends");
        friendsCol.setCellValueFactory(cell ->
                new SimpleStringProperty(serviceUser.getFriendNames(cell.getValue().getFriends()))
        );

        TableColumn<Duck, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Duck, Double> speedCol = new TableColumn<>("Speed");
        speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));

        TableColumn<Duck, Double> rezCol = new TableColumn<>("Rezistance");
        rezCol.setCellValueFactory(new PropertyValueFactory<>("rezistance"));

        tableDucks.getColumns().addAll(
                idCol, usernameCol, emailCol, friendsCol, typeCol, speedCol, rezCol
        );
    }


    private void initPersonsTable() {
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
        friendsCol.setCellValueFactory(cell ->
                new SimpleStringProperty(serviceUser.getFriendNames(cell.getValue().getFriends()))
        );

        tablePersons.getColumns().addAll(Arrays.asList(
                idCol, usernameCol, emailCol, nameCol, surnameCol,
                occupationCol, dobCol, empathyCol, friendsCol
        ));
    }

    /* ---------------------- PAGINATION ---------------------- */

    private void initDucksPagination() {
        if (ducksPagination == null) ducksPagination = new Pagination();

        ducksPagination.setPageCount(
                (serviceUser.duckCount(getSelectedDuckType()) + DUCKS_PER_PAGE - 1) / DUCKS_PER_PAGE
        );
        ducksPagination.setPageFactory(this::getDucksPage);
    }

    private Node getDucksPage(int pageIndex) {
        List<Duck> ducks = serviceUser.getDucksPage(pageIndex, DUCKS_PER_PAGE, getSelectedDuckType());
        tableDucks.getItems().setAll(ducks);
        return new VBox();
    }

    private void initPersonsPagination() {
        if (personsPagination == null) personsPagination = new Pagination();

        personsPagination.setPageCount(serviceUser.personCount());
        personsPagination.setPageFactory(this::getPersonsPage);
    }

    private Node getPersonsPage(int pageIndex) {
        List<Person> persons = serviceUser.getPersonsPage(pageIndex, PERSONS_PER_PAGE);
        tablePersons.getItems().setAll(persons);
        return new VBox();
    }

    private DuckType getSelectedDuckType() {
        String selected = filterBox.getValue();
        return selected.equals("NONE") ? null : DuckType.valueOf(selected);
    }

    /* ---------------------- BUTTONS BAR ---------------------- */

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

        Button chatBtn = new Button("Chat");
        chatBtn.setOnAction(e -> {
            ChatWindow window = new ChatWindow(serviceUser, serviceMessage, currentUser.getId());
            serviceMessage.addObserver(window);
            window.show();
        });

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
                    Stage stage = (Stage) logoutBtn.getScene().getWindow();
                    stage.close();
                    LoginWindow loginWindow = new LoginWindow(serviceUser, serviceMessage,serviceFriendRequest);
                    loginWindow.start(new Stage());
        });

        Button friendRequestBtn = new Button("Send Friend Request");
        friendRequestBtn.setOnAction(e->{
           sendFriendRequestFx();
        });

        Button checkFriendRequestsBtn = new Button("Check Friend Requests");
        checkFriendRequestsBtn.setOnAction(e->{
            checkFriendRequests();
        });
        HBox h = new HBox(10,
                addUserBtn, removeUserBtn,
                addFriendBtn, removeFriendBtn,
                communitiesBtn, largestBtn,
                chatBtn, logoutBtn,
                friendRequestBtn, checkFriendRequestsBtn
        );
        h.setPadding(new Insets(10));
        h.setAlignment(Pos.CENTER);
        return h;
    }




    @Override
    public void update() {
        initDucksPagination();
        initPersonsPagination();
        refreshFriendRequestNotifications();
    }

    /* ---------------------- HELPERS ---------------------- */

    private String input(String text) {
        TextInputDialog d = new TextInputDialog();
        d.setHeaderText(text);
        return d.showAndWait().orElse("");
    }
    

    /* ---------------------- FUNCTIONALITY ---------------------- */

    public void addUserFx() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Adaugă utilizator");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setPrefHeight(500);

        GridPane grid = new GridPane();
        grid.setHgap(15); grid.setVgap(10); grid.setPadding(new Insets(10));

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
        grid.add(new Label("Tip utilizator:"), 0, r); grid.add(userType, 1, r); r++;

        VBox duckBox = new VBox(8,
                new Label("DuckType:"), duckType,
                new Label("Viteză:"), speed,
                new Label("Rezistență:"), rez
        );
        duckBox.setVisible(true);
        duckBox.setManaged(true);
        grid.add(duckBox, 0, r, 4, 1); r++;

        VBox personBox = new VBox(8,
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
            boolean isDuck = userType.getValue().equals("duck");
            duckBox.setVisible(isDuck); duckBox.setManaged(isDuck);
            personBox.setVisible(!isDuck); personBox.setManaged(!isDuck);
        });

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                try {
                    if (userType.getValue().equals("duck")) {
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
                    InformationAlert.alert("Eroare: " + e.getMessage());
                }
            }
        });
    }

    public void removeUserFx() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Sterge utliziator");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(15); grid.setVgap(15); grid.setPadding(new Insets(10));

        TextField username = new TextField();
        grid.add(new Label("Nume utilizator:"), 0, 0);
        grid.add(username, 1, 0);
        dialog.getDialogPane().setContent(grid);
        dialog.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                try {
                    String u = username.getText();
                    serviceUser.removeUser(u);
                    InformationAlert.alert("User sters cu succes!");
                } catch (Exception e) {
                    InformationAlert.alert("Eroare: " + e.getMessage());
                }
            }
        });
    }

    public void addFriendFx() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Adaugă prietenie");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(15); grid.setVgap(10); grid.setPadding(new Insets(10));

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
                    String username = userIdField.getText();
                    String friendUsername = friendIdField.getText();
                    serviceUser.addFriend(username, friendUsername);
                    serviceUser.addFriend(friendUsername, username);
                    InformationAlert.alert("Prietenie adaugata!");
                } catch (Exception e) {
                    InformationAlert.alert("Eroare: " + e.getMessage());
                }
            }
        });
    }

    public void removeFriendFx() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Șterge prietenie");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(15); grid.setVgap(10); grid.setPadding(new Insets(10));

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
                    InformationAlert.alert("Prietenie ștearsă!");
                } catch (Exception e) {
                    InformationAlert.alert("Eroare: " + e.getMessage());
                }
            }
        });
    }

    private void sendFriendRequestFx() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Trimitere cerere de prietenie");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(15); grid.setVgap(15); grid.setPadding(new Insets(10));

        TextField username = new TextField();
        grid.add(new Label("Nume utilizator:"), 0, 0);
        grid.add(username, 1, 0);
        dialog.getDialogPane().setContent(grid);
        dialog.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                try {
                    String u = username.getText();
                    serviceFriendRequest.addFriendRequest(currentUser.getId(),serviceUser.getUserByUsername(u).getId());
                    InformationAlert.alert("Prietenie trimisa cu succes!");
                } catch (Exception e) {
                    InformationAlert.alert("Eroare: " + e.getMessage());
                }
            }
        });
    }

    public void showNumberOfCommunitiesFx() {
        try {
            int nr = serviceUser.getNumberOfCommunities();
            InformationAlert.alert("Număr comunități: " + nr);
        } catch (Exception e) {
            InformationAlert.alert("Eroare: " + e.getMessage());
        }
    }

    public void showLargestCommunityFx() {
        try {
            List<User> community = serviceUser.getBiggestCommunity();
            StringBuilder users = new StringBuilder();
            for (User u : community) {
                users.append(u.getId()).append(" ").append(u.getUsername()).append("\n");
            }
            InformationAlert.alert(users.toString());
        } catch (Exception e) {
            InformationAlert.alert("Eroare: " + e.getMessage());
        }
    }

    public void checkFriendRequests(){
        FriendRequestWindow window = new FriendRequestWindow(serviceFriendRequest,serviceUser,currentUser.getId());
        window.show();
    }




}
