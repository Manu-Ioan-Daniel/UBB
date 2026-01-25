package controllers;

import enums.ChangeEvent;
import enums.Hobby;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Hotel;
import models.SpecialOffer;
import services.MainService;
import utils.StageManager;
import utils.observer.Observer;
import models.Client;
import java.time.LocalDate;
import static javafx.collections.FXCollections.observableList;

public class WindowController implements Observer {

    @FXML
    private TableView<SpecialOffer> offersTable;

    @FXML
    private TableColumn<SpecialOffer, LocalDate> endDateColumn;

    @FXML
    private TableColumn<SpecialOffer, String> hotelNameColumn;

    @FXML
    private TableColumn<SpecialOffer, String> locationNameColumn;

    @FXML
    private TableColumn<SpecialOffer, LocalDate> startDateColumn;

    @FXML
    private DatePicker reservationDatePicker;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField numberOfNightsField;


    private MainService mainService;
    private Client currentClient;
    private StageManager stageManager;


    public void handleReservation(){
        SpecialOffer selectedOffer = offersTable.getSelectionModel().getSelectedItem();
        if(selectedOffer == null){
            stageManager.showErrorAlert("No offer selected!");
            return;
        }
        String nightsText = numberOfNightsField.getText();
        try{
            int nights = Integer.parseInt(nightsText);
            mainService.saveReservation(currentClient, selectedOffer, reservationDatePicker.getValue(), nights);
        } catch(Exception e) {
            stageManager.showErrorAlert(e.getMessage());
        }
    }

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    public void setClient(Client client) {
        this.currentClient = client;
    }

    public void initOffersTable() {
        hotelNameColumn.setCellValueFactory(cell -> {
            String hotelName = mainService.findHotelById(cell.getValue().getHotelId()).getHotelName();
            return new SimpleStringProperty(hotelName);
        });
        locationNameColumn.setCellValueFactory(cell -> {
            Hotel hotel = mainService.findHotelById(cell.getValue().getHotelId());
            String locationName = mainService.findLocationById(hotel.getLocationId()).getLocationName();
            return new SimpleStringProperty(locationName);
        });
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        offersTable.setItems(observableList(mainService.findClientSpecialOffers(currentClient)));
    }

    public void init(){
        usernameLabel.setText(currentClient.getName());
        stageManager = new StageManager();
        reservationDatePicker.setValue(LocalDate.now());
        initOffersTable();
        mainService.addObserver(this);
    }

    private void loadOffersTable(){
        offersTable.setItems(observableList(mainService.findClientSpecialOffers(currentClient)));
    }

    @Override
    public void update(ChangeEvent event) {
        if(event==ChangeEvent.RESERVATION_SAVED){
            loadOffersTable();
            if(mainService.getLastReservationClient().getId().equals(currentClient.getId())){
                return;
            }
            String message = generateMessage();
            if(message.isEmpty()){
                return;
            }
            stageManager.showInformationAlert(message);
        }
    }

    private String generateMessage() {
        Client client = mainService.getLastReservationClient();
        boolean foundHobby = false;
        StringBuilder message = new StringBuilder("New reservation made by " + client.getName() + "with some hobbies like you, " + currentClient.getName() + " :");
        for(Hobby hobby : client.getHobbies()){
            if(currentClient.getHobbies().contains(hobby)){
                message.append(hobby.toString()).append(" ");
                foundHobby = true;
            }
        }
        if(!foundHobby){
            return "";
        }
        return message.toString();
    }
}
