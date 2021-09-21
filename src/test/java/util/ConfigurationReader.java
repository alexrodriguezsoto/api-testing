package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {


    private static final Properties properties;

    static {
        try {
//            String configPath = "../api-testing/configuration.properties";
            String configPath = "configuration.properties";

            FileInputStream fileInputStream = new FileInputStream(configPath);
                properties = new Properties();
                properties.load(fileInputStream);
                fileInputStream.close();

        } catch (IOException e){
            e.printStackTrace();;
            throw new RuntimeException("Failed to load properties file!");
        }
    }
    public static String getProperty(String propertyName){
        return properties.getProperty(propertyName);
    }
}


    // ESTE es tu pinche config del batch 2 de tu cole, funca con DB creo pero no el api
//    public static String readProperty(String property, String filePath) {
//        Properties prop = null;
//
//        try {
//            FileInputStream fileInput = new FileInputStream(filePath);
//            prop = new Properties();
//            prop.load(fileInput);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//
//        return prop.getProperty(property);
//    }

//}