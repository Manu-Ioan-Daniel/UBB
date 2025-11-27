package Ui;
import domain.Duck;
import enums.DuckType;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import observer.Observer;
import repo.DatabaseUserRepository;
import service.ServiceUser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UiJavaFx extends Application implements Observer {
    private TableView<Duck> tableDucks;
    private ServiceUser serviceUser;
    private ComboBox<String> filterBox;
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        String url = "jdbc:postgresql://localhost:5432/MAP?user=postgres&password=123";
        DatabaseUserRepository userRepo = new DatabaseUserRepository(url);
        serviceUser = new ServiceUser(userRepo);
        serviceUser.addObserver(this);
        initializeDucksTable();
        initializeFilterBox();
        applyFilter();
        root.setBottom(filterBox);
        root.setCenter(tableDucks);
        Scene scene = new Scene(root, 700, 400);
        stage.setScene(scene);
        stage.setTitle("All Ducks Table");
        stage.show();
    }
    private void initializeDucksTable(){
        tableDucks = new TableView<>();
        TableColumn<Duck, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Duck, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Duck, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Duck,String> friendsCol=new TableColumn<>("Friends");
        friendsCol.setCellValueFactory(cell->{
            String friendsStr=cell.getValue().getFriends().toString();
            return new SimpleStringProperty(friendsStr);
        });
        TableColumn<Duck, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Duck, Double> speedCol = new TableColumn<>("Speed");
        speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));

        TableColumn<Duck, Double> rezistanceCol = new TableColumn<>("Rezistance");
        rezistanceCol.setCellValueFactory(new PropertyValueFactory<>("rezistance"));
        tableDucks.getColumns().addAll(
                java.util.Arrays.asList(idCol, usernameCol, emailCol, friendsCol, typeCol, speedCol, rezistanceCol)
        );
    }
    private void initializeFilterBox() {
        filterBox = new ComboBox<>();
        filterBox.getItems().addAll("NONE", "SWIMMING", "FLYING");
        filterBox.setValue("NONE");
        filterBox.setOnAction(event -> applyFilter());
    }
    private void applyFilter() {
        String selected = filterBox.getValue();
        if (selected.equals("NONE")) {
            tableDucks.getItems().setAll(serviceUser.getDucks());
            return;
        }
        DuckType type = DuckType.valueOf(selected);

        tableDucks.getItems().setAll(serviceUser.getDucksByType(type));
    }

    @Override
    public void update() {
        applyFilter();
    }
    public static void main(String[] args) {
        launch();
    }
    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }
}
