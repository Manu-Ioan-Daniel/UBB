package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class FXMLUtils {
    public static <T> Tuple<Scene,T> load(String path){
        try{
            FXMLLoader loader = new FXMLLoader(FXMLUtils.class.getResource(path));
            Scene scene = new Scene(loader.load());
            return new Tuple<>(scene,loader.getController());
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }
}
