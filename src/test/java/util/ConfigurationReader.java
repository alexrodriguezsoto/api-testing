package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    private static final Properties properties;

    static {
        try {
            // this is the path to the location of the file
            String configPath = "configuration.properties";

            // java cannot read files directly, it needs inputStream to read files
            // inputStream takes the location of the file as a constructor
            FileInputStream fileInputStream = new FileInputStream(configPath);
                properties = new Properties(); // Properties is used to read specifically properties files, files with key value pairs.
                properties.load(fileInputStream);  // file contents are loaded to properties from the inputStream
                fileInputStream.close(); // all input streams must be closed

        } catch (IOException e){
            e.printStackTrace();;
            throw new RuntimeException("Failed to load properties file!");
        }
    }
    public static String getProperty(String propertyName){
        return properties.getProperty(propertyName);
    }
}
