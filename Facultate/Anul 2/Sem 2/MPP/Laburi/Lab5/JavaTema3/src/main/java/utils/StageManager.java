package utils;

import controllers.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repos.EmployeeDBRepo;
import services.AuthService;
import services.MainService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class StageManager {

    public static void openLoginWindow(AuthService authService) {
        Tuple<Scene, LoginController> tuple = FXMLUtil.load(("/view/login.fxml"));
        tuple.getSecond().setAuth(authService);
        Scene scene = tuple.getFirst();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static void openMainWindow() {
        Tuple<Scene, MainController> tuple = FXMLUtil.load(("/view/mainView.fxml"));
        tuple.getSecond().init(ServiceFactory.getInstance().getMainService());
        Scene scene = tuple.getFirst();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static void openModifyWindow(MainService mainService) {
        Tuple<Scene, ModifyController> tuple = FXMLUtil.load(("/view/modifyView.fxml"));
        tuple.getSecond().init(mainService);
        Scene scene = tuple.getFirst();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    public static void openReservationForm(MainService mainService,Long rideId) {
        Tuple<Scene, ReservationFormController> tuple = FXMLUtil.load(("/view/reservationForm.fxml"));
        tuple.getSecond().init(mainService,rideId);
        Scene scene = tuple.getFirst();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static void openSearchForm(MainService mainService){
        Tuple<Scene, SearchFormController> tuple = FXMLUtil.load(("/view/searchForm.fxml"));
        tuple.getSecond().init(mainService);
        Scene scene = tuple.getFirst();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


}