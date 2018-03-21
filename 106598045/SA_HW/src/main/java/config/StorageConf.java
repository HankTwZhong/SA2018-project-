package config;

import java.io.FileInputStream;
import java.util.Properties;

public class StorageConf {
    private static String method = null;
    public static String getStorageMethod(){
        if (method == null) {
            Properties properties = new Properties();
            String path = ""+ StorageConf.class.getClassLoader().getResource("");
            String configFile = path.substring(6)+"storageConf.properties";
            try {
                properties.load(new FileInputStream(configFile));
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
            method = properties.getProperty("storageMethod");
        }
        return method;
    }
}
