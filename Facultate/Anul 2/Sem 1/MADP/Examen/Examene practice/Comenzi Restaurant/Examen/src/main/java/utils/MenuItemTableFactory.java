package utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.MenuItem;

public class MenuItemTableFactory {
    private static MenuItemTableFactory instance = null;

    private MenuItemTableFactory() {}

    public static MenuItemTableFactory getInstance() {
        if (instance == null) {
            instance = new MenuItemTableFactory();
        }
        return instance;
    }
    public TableView<MenuItem> createMenuItemTable() {
        TableView<MenuItem> tableView = new TableView<>();
        TableColumn<MenuItem, String> name = new TableColumn<>("Item name");
        TableColumn<MenuItem, String> price = new TableColumn<>("Price");
        name.setCellValueFactory(new PropertyValueFactory<>("item"));
        price.setCellValueFactory(cell->{
            MenuItem item = cell.getValue();
            return new SimpleStringProperty(item.getPrice() + " "+item.getCurrency());
        });
        tableView.getColumns().add(name);
        tableView.getColumns().add(price);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        name.setStyle( "-fx-alignment: CENTER;");
        price.setStyle( "-fx-alignment: CENTER;");
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        return tableView;
    }
}
