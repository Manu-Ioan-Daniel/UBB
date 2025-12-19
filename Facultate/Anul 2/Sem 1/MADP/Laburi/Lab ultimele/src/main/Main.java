package main;

import Ui.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import repo.DatabaseFriendRequestRepository;
import repo.DatabaseMessageRepository;
import repo.DatabaseUserRepository;
import service.ServiceFriendRequest;
import service.ServiceMessage;
import service.ServiceUser;


public class Main extends Application{
    public static void main(String[] args) {
       launch();
    }
    @Override
    public void start(Stage stage){
        String url = "jdbc:postgresql://localhost:5432/MAP?user=postgres&password=123";
        DatabaseUserRepository userRepo = new DatabaseUserRepository(url);
        ServiceUser serviceUser = new ServiceUser(userRepo);
        ServiceMessage serviceMessage=new ServiceMessage(new DatabaseMessageRepository(url));
        ServiceFriendRequest serviceFriendRequest = new ServiceFriendRequest(new DatabaseFriendRequestRepository(url));
        LoginWindow loginWindow = new LoginWindow(serviceUser,serviceMessage,serviceFriendRequest);
        loginWindow.start(new Stage());
        LoginWindow loginWindow2 = new LoginWindow(serviceUser,serviceMessage,serviceFriendRequest);
        loginWindow2.start(new Stage());
    }
}
