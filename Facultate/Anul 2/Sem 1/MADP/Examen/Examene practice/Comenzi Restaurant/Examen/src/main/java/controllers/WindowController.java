package controllers;

import enums.ChangeEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import models.MenuItem;
import models.Table;
import services.MainService;
import utils.MenuItemTableFactory;
import utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WindowController implements Observer {
    @FXML
    private Label usernameLabel;

    @FXML
    private VBox tablesVBox;

    List<TableView<MenuItem>> tables = new ArrayList<>();
    private MainService mainService;
    private Table currentTable;



    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    public void setCurrentTable(Table table) {
        this.currentTable = table;
    }

    private void loadMenuItemTables() {
        Map<String, List<MenuItem>> menuItemsByCategory = mainService.menuItemsByCategory();
        for(String category : menuItemsByCategory.keySet()){

            Label categoryLabel = new Label(category);
            categoryLabel.setStyle("-fx-font-size:24px;");
            tablesVBox.getChildren().add(categoryLabel);

            TableView<MenuItem> tableView = MenuItemTableFactory.getInstance().createMenuItemTable();
            tableView.setItems(FXCollections.observableList(menuItemsByCategory.get(category)));
            tablesVBox.getChildren().add(tableView);

            tables.add(tableView);
        }
    }

    public void handleOrder() {
        List<MenuItem> selectedItems = new ArrayList<>();
        for (TableView<MenuItem> tableView : tables) {
            List<MenuItem> items = tableView.getSelectionModel().getSelectedItems();
            selectedItems.addAll(items);
        }
        if (!selectedItems.isEmpty()) {
            mainService.placeOrder(currentTable.getId(), selectedItems);
        }
    }

    public void init(){
        usernameLabel.setText("Table: " + currentTable.getId());
        loadMenuItemTables();
    }



    @Override
    public void update(ChangeEvent event) {

    }
}
