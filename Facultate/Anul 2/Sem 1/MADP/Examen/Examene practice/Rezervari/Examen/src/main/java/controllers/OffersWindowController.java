package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.SpecialOffer;
import services.MainService;

import java.time.LocalDate;

import static javafx.collections.FXCollections.observableList;

public class OffersWindowController {

    @FXML
    private TableView<SpecialOffer> offersTable;

    @FXML
    private TableColumn<SpecialOffer, Double> discountColumn;

    @FXML
    private TableColumn<SpecialOffer, LocalDate> startDateColumn;

    @FXML
    private TableColumn<SpecialOffer, LocalDate> endDateColumn;

    @FXML
    private DatePicker startDatePicker;

    private MainService mainService;
    private Long hotelId;

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    public void setHotelId(Long id) {
        this.hotelId = id;
    }

    private void initOffersTable(){
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("percents"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        offersTable.setItems(observableList(mainService.findSpecialOffers(hotelId,startDatePicker.getValue())));
    }

    public void init() {
        initDatePicker();
        initOffersTable();
    }

    public void initDatePicker(){

        startDatePicker.setValue(LocalDate.now());

        startDatePicker.valueProperty().addListener((options, oldValue, newValue) -> {
            if(newValue != null){
                offersTable.setItems(observableList(mainService.findSpecialOffers(hotelId, newValue)));
            }
        });

    }
}
