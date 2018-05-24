package ntut.sa2018.Controller.HostManagement;

import ntut.sa2018.UseCase.Interface.HostManagementRepository;

import java.io.FileInputStream;
import java.util.Properties;

public class HostManagementDirector {
    public static HostManagementRepository StorageBuild() {
        Properties properties = new Properties();
        String path = ""+ HostManagementDirector.class.getClassLoader().getResource("");
        String configFile = path.substring(6) + "storageConf.properties";
        try {
            properties.load(new FileInputStream(configFile));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String storageType = properties.getProperty("storageMethod");
        if(storageType.equals("TXT")) {
            return new HostManagementTxt();
        } else if(storageType.equals("DB")) {
            return new HostManagementMongoDB();
        } else {
            return null;
        }
    }
}