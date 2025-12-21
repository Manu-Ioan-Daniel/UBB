package main;

import controller.LoginController;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.UserModel;


public class Main extends Application {

    @Override
    public void start(Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginWindow.fxml"));
            Parent root = loader.load();
            LoginController controller = loader.getController();
            UserModel userModel =new UserModel();
            controller.setUserModel(userModel);
            Scene scene = new Scene(root);

            stage.setTitle("Duck Social Network");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
