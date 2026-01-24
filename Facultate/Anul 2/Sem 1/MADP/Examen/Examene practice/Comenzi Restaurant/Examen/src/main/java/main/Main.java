package main;

import javafx.application.Application;
import javafx.stage.Stage;
import repo.DbTableRepository;
import repo.MenuItemRepository;
import repo.OrderRepository;
import services.MainService;
import utils.StageManager;


public class Main extends Application {

    @Override
    public void start(Stage stage){
        try {
            StageManager stageManager = new StageManager();
            MainService mainService = new MainService(new DbTableRepository(),new MenuItemRepository(),new OrderRepository());
            stageManager.showMainWindow(stage,mainService);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


}
