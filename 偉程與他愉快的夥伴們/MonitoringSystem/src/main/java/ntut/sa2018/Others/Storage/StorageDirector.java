package ntut.sa2018.Others.Storage;

import ntut.sa2018.Others.Interface.StorageInterface;

import java.io.FileInputStream;
import java.util.Properties;

public class StorageDirector {
    public static StorageInterface StorageBuild() {
        Properties properties = new Properties();
        String path = ""+ StorageDirector.class.getClassLoader().getResource("");
        String configFile = path.substring(6) + "storageConf.properties";
        try {
            properties.load(new FileInputStream(configFile));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String storageType = properties.getProperty("storageMethod");
        if(storageType.equals("TXT")) {
            return new StorageTxt();
        } else if(storageType.equals("DB")) {
            return new StorageMongoDB();
        } else {
            return null;
        }
    }
}