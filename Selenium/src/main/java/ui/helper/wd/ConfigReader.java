package ui.helper.wd;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class ConfigReader {
    public static final Logger LOGGER = Logger.getLogger(ConfigReader.class);
    private static final String CONFIG_PATH;
    private static final String CONFIG_PATH_DEFAULT;
    private static Properties properties;

    public ConfigReader() {
    }

    private static void loadConfigPropertiesFile(String path) {
        FileInputStream inpstrm = null;

        try {
            File e = new File(CONFIG_PATH_DEFAULT + path);
            if(!e.isFile()) {
                e = new File(CONFIG_PATH + path);
            }

            inpstrm = new FileInputStream(e);
            properties.load(new InputStreamReader(inpstrm));
        } catch (Exception var6) {
            throw new IllegalStateException(var6 + "Path not found: " + CONFIG_PATH_DEFAULT + path);
        } finally {
            IOUtils.closeQuietly(inpstrm);
        }

    }

    public static String getValueByKey(String key) {
        loadConfigPropertiesFile("config.properties");
        Object obj = properties.get(key);
        if(obj == null) {
            LOGGER.info("Value is null, key is : " + key);
        }

        return String.valueOf(obj);
    }

    public static void setValue(String key, String value) throws IOException {
        loadConfigPropertiesFile("config.properties");
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(CONFIG_PATH + "config.properties");
            properties.setProperty(key, value);
            properties.store(out, (String)null);
        } finally {
            IOUtils.closeQuietly(out);
        }

    }

    static {
        CONFIG_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "/resources" + File.separator;
        CONFIG_PATH_DEFAULT = System.getProperty("user.dir") + File.separator + "target" + File.separator + "classes" + File.separator;
        properties = new Properties();
    }
}
