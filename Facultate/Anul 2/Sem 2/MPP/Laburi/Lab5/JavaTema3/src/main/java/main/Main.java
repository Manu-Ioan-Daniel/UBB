package main;

import javafx.application.Application;
import javafx.stage.Stage;
import repos.EmployeeDBRepo;
import services.AuthService;
import utils.ServiceFactory;
import utils.StageManager;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        StageManager.openLoginWindow(ServiceFactory.getInstance().getAuthService());
    }
}
