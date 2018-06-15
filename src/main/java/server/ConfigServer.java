package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigServer {
    private static final String PROP_FILE = "res/server.properties";

    public static int PORT;
    public static String CONNECTION_MSG;
    public static int HISTORY_LENGTH;

    static {
        Properties properties = new Properties();

        try (FileInputStream propFile = new FileInputStream(PROP_FILE)) {
            properties.load(propFile);
            PORT = Integer.valueOf(properties.getProperty("PORT"));
            CONNECTION_MSG = String.valueOf(properties.getProperty("CONNECTION_MSG"));
            HISTORY_LENGTH = Integer.valueOf(properties.getProperty("HISTORY_LENGTH"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
