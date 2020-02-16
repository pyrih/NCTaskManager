package ua.edu.sumdu.j2se.pyrih.tasks.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class PropertyLoader {
    private static final Logger logger = Logger.getLogger(PropertyLoader.class);

    /**
     * Returns set of mail properties.
     *
     * @param path
     * @return set of mail properties.
     */
    public static Properties getProperties(String path) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            URL url = PropertyLoader.class.getResource(path);
            inputStream = url.openStream();
            properties.load(inputStream);
            logger.info("Property configuration file was successfully downloaded.");
        } catch (IOException e) {
            logger.error("Cannot find property file: " + inputStream, e);
        }
        return properties;
    }
}
