package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        try {
            Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/view/loginWindow.fxml"))));
            Scene scene = new Scene(root);

            stage.setTitle("Duck Social Network");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
