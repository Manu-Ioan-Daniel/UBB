package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class FXMLUtil {
    public static <T> Tuple<Scene,T> load(String path){
        try{
            FXMLLoader loader = new FXMLLoader(FXMLUtil.class.getResource(path));
            Scene scene = new Scene(loader.load());
            return new Tuple<>(scene,loader.getController());
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }
}
