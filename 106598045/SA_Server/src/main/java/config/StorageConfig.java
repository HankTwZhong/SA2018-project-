package config;

import java.io.FileInputStream;
import java.util.Properties;

import Repository.StorageInterface;
import Storage.StorageMongoDB;
import Storage.StorageTxt;

public class StorageConfig {
    public static StorageInterface Build() {
        Properties properties = new Properties();
        String path = ""+ StorageConfig.class.getClassLoader().getResource("");
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