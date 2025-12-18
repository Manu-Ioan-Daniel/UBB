package utils;

import javafx.scene.control.Alert;

public class InformationAlert {
    public static void alert(String message){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(message);
            a.showAndWait();
    }
}
