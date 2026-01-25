package main;

import javafx.application.Application;
import javafx.stage.Stage;
import repo.*;
import services.MainService;
import utils.StageManager;


public class Main extends Application {


    @Override
    public void start(Stage stage) {
        StageManager stageManager = new StageManager();
        MainService mainService = new MainService(new LocationRepo(), new HotelRepo(), new SpecialOfferRepo(),new ClientRepo(),new ReservationRepo());
        stageManager.showMainWindow(stage, mainService);
    }

}
