package utils;

import java.io.FileReader;
import java.util.Properties;

public class Props {

    private static final String configFilePath = "src/main/resources/bd.config";

    public static Properties getProperties(){
        Properties prop = new Properties();
        try{
            prop.load(new FileReader(configFilePath));
        } catch (Exception e) {
            System.err.println("Cannot find bd.config");
        }
        return prop;
    }
}
