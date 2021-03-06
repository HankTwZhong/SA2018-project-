package Monitor;

import java.io.FileInputStream;
import java.util.Properties;

import Storage.StorageMongoDB;
import Storage.StorageTxt;

public class StorageDirector {
    public static StorageBuilder Build() {
        Properties properties = new Properties();
        String path = ""+ StorageDirector.class.getClassLoader().getResource("");
        String configFile = path.substring(6) + "storageConf.properties";
        try {
            properties.load(new FileInputStream(configFile));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String storageType = properties.getProperty("storageMethod");
        System.out.println(storageType);

        if(storageType.equals("TXT")) {
            return new StorageTxt();
        } else if(storageType.equals("DB")) {
            return new StorageMongoDB();
        } else {
            return null;
        }
    }
}