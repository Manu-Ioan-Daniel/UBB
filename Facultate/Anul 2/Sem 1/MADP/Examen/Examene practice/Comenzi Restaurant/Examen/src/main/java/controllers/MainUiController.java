package controllers;

import enums.ChangeEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Order;
import services.MainService;
import utils.StageManager;
import utils.observer.Observer;

import java.time.LocalDateTime;

public class MainUiController implements Observer {

    @FXML
    private TableColumn<Order, LocalDateTime> dateColumn;

    @FXML
    private TableColumn<Order, String> itemNameColumn;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, Long> tableIdColumn;

    private MainService mainService;
    private StageManager stageManager;



    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    private void loadPlacedOrders() {
       ordersTable.setItems(FXCollections.observableList(mainService.findPlacedOrders()));
    }

    private void initOrdersTable(){
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableIdColumn.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        itemNameColumn.setCellValueFactory(cell->{
            Long orderId = cell.getValue().getId();
            String itemNames = mainService.getItemNamesForOrder(orderId);
            return new SimpleStringProperty(itemNames);
        });
    }

    public void init(){
        stageManager = new StageManager();
        stageManager.showWindows(mainService.findAllTables(),mainService);
        mainService.addObserver(this);
        initOrdersTable();
        loadPlacedOrders();
    }

    @Override
    public void update(ChangeEvent event) {
        if(event == ChangeEvent.ORDER_PLACED){
            loadPlacedOrders();
        }
    }
}
