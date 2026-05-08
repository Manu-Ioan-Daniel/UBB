import javafx.application.Application;
import javafx.stage.Stage;
import proxy.ServicesObjectProxy;
import utils.StageManager;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
       StageManager.openLoginWindow(new ServicesObjectProxy("127.0.0.1",5555));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

