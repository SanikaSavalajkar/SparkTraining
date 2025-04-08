package util;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
    public static String getPropertyString(String fileName) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("resources/" + fileName)) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.getProperty("db.url") + "?user=" + props.getProperty("db.user") + "&password=" + props.getProperty("db.password");
    }
}