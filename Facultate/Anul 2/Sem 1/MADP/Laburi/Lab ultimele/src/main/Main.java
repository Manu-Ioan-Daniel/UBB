package main;

import Ui.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import repo.DatabaseUserRepository;
import service.ServiceUser;

public class Main extends Application{
    private static ServiceUser service;
    public static void main(String[] args) {
       launch();
    }
    public static ServiceUser getService() {
        return service;
    }

    @Override
    public void start(Stage stage){
        String url = "jdbc:postgresql://localhost:5432/MAP?user=postgres&password=123";
        DatabaseUserRepository userRepo = new DatabaseUserRepository(url);
        service = new ServiceUser(userRepo);
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.start(new Stage());
    }
}
