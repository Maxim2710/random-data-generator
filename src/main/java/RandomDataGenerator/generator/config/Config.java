package RandomDataGenerator.generator.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Error: Unable to find config.properties file.");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error loading configuration properties.", ex);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String[] getOutputFormats() {
        String formats = properties.getProperty("output.formats", "json,xml,csv");
        return formats.split(",");
    }

    public static String getOutputDirectory() {
        return properties.getProperty("output.directory", "generated_data");
    }

    public static long getOutputMaxSize() {
        return Long.parseLong(properties.getProperty("output.maxSize", "10485760"));
    }
}
