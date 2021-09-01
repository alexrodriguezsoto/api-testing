package util;

import java.io.FileInputStream;
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
