package controllers;

import enums.ChangeEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Driver;
import models.Order;
import services.MainService;
import utils.StageManager;
import utils.observer.Observer;
import java.time.LocalDateTime;

public class WindowController implements Observer {

    @FXML
    private AnchorPane root;

    @FXML
    private Label driverNameLabel;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, Void> markColumn;

    @FXML
    private TableColumn<Order, String> nameColumn;

    @FXML
    private TableColumn<Order, String> pickupColumn;

    @FXML
    private TableColumn<Order, String> destinationColumn;

    @FXML
    private TableColumn<Order, LocalDateTime> startDateColumn;



    private Driver currentDriver;

    private MainService mainService;
    private StageManager stageManager;

    public void setCurrentDriver(Driver driver){
        currentDriver = driver;
    }

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    public void initOrdersTable(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        pickupColumn.setCellValueFactory(new PropertyValueFactory<>("pickupAdress"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destinationAdress"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        markColumn.setCellFactory(tc -> new TableCell<Order, Void>() {

            private final Button btn = new Button("Finished");
            {
                btn.setOnAction(event -> {
                    Order order = getTableView().getItems().get(getIndex());
                    mainService.finishOrder(order);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
        loadDriverOrders();
    }

    public void loadDriverOrders(){
        ordersTable.setItems(FXCollections.observableList(
                mainService.findActiveOrdersByDriverId(currentDriver.getId())
        ));
    }

    public void init(){
        stageManager = new StageManager();
        driverNameLabel.setText(currentDriver.getName());
        mainService.addObserver(this);
        initOrdersTable();
    }

    public void showDialog(){
        if(currentDriver.getId().equals(mainService.findQualifiedDriver().getId())){
            ((Stage)root.getScene().getWindow()).toFront();
            stageManager.showConfirmationAlert(this::updateOrder,mainService.getOrderString());
        }
    }

    public void updateOrder(){
        try{
            mainService.updateOrder(currentDriver.getId());
        } catch (Exception e) {
            stageManager.showErrorAlert(e.getMessage());
        }
    }

    @Override
    public void update(ChangeEvent event) {
        loadDriverOrders();
        if(ChangeEvent.ORDER_ADDED == event){
            showDialog();
        }
    }
}
