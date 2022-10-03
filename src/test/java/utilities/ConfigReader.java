package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        String path = "/Users/cynaraismailova/TGProject/src/test/resources/configurations/Configuration.properties";
        FileInputStream input = null;
        try {
            input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
        } catch (FileNotFoundException e) {
            System.out.println("Properties file oath is invalid");
        } catch (IOException e) {
            System.out.println("failed to load properties file");
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                System.out.println("Eception thrown while closing FileInputStream object");
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

