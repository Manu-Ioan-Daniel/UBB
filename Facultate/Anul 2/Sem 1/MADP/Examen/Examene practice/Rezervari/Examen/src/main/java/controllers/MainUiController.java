package controllers;

import enums.ChangeEvent;
import enums.HotelType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Hotel;
import models.Location;
import services.MainService;
import utils.StageManager;
import utils.observer.Observer;


import static javafx.collections.FXCollections.observableList;

public class MainUiController implements Observer {

    @FXML
    private TableColumn<Hotel, String> hotelNameColumn;

    @FXML
    private TableView<Hotel> hotelsTable;

    @FXML
    private ComboBox<String> locationsComboBox;

    @FXML
    private TableColumn<Hotel, Double> priceColumn;

    @FXML
    private TableColumn<Hotel, Integer> roomNumberColumn;

    @FXML
    private TableColumn<Hotel, HotelType> typeColumn;

    private MainService mainService;
    private StageManager stageManager;

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }


    public void initComboBoxData(){
        locationsComboBox.setItems(observableList(mainService.findAllLocations().stream().map(Location::getLocationName).toList()));
        locationsComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            hotelsTable.setItems(observableList(mainService.findHotelsByLocation(newValue)));
        });
        locationsComboBox.setValue(locationsComboBox.getItems().get(0));
    }

    public void initHotelsTable(){
        hotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("noRooms"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        hotelsTable.setItems(observableList(mainService.findHotelsByLocation(locationsComboBox.getValue())));
        hotelsTable.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue != null){
                stageManager.showOffersWindow(newValue.getId(),mainService);
            }
        });
    }

    public void init(){

        stageManager = new StageManager();
        initComboBoxData();
        initHotelsTable();
        stageManager.showWindows(mainService.findAllClients(),mainService);

    }



    @Override
    public void update(ChangeEvent event) {

    }
}
