package utils;

import controllers.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.IAuthService;
import services.IMainService;


public class StageManager {

    public static void openLoginWindow(IAuthService authService) {
        Tuple<Scene, LoginController> tuple = FXMLUtil.load(("/view/login.fxml"));
        Scene scene = tuple.getFirst();
        tuple.getSecond().setAuth(authService);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static void openMainWindow(IMainService mainService) {
        Tuple<Scene, MainController> tuple = FXMLUtil.load(("/view/mainView.fxml"));
        tuple.getSecond().init(mainService);
        Scene scene = tuple.getFirst();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static void openModifyWindow(IMainService mainService) {
        Tuple<Scene, ModifyController> tuple = FXMLUtil.load(("/view/modifyView.fxml"));
        tuple.getSecond().init(mainService);
        Scene scene = tuple.getFirst();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    public static void openReservationForm(IMainService mainService,Long rideId) {
        Tuple<Scene, ReservationFormController> tuple = FXMLUtil.load(("/view/reservationForm.fxml"));
        tuple.getSecond().init(mainService,rideId);
        Scene scene = tuple.getFirst();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static void openSearchForm(IMainService mainService){
        Tuple<Scene, SearchFormController> tuple = FXMLUtil.load(("/view/searchForm.fxml"));
        tuple.getSecond().init(mainService);
        Scene scene = tuple.getFirst();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


}