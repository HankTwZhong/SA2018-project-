package Servlet;

import Repository.*;
import java.io.FileInputStream;
import java.util.Properties;

public class HostRepositoryBuilder {

    public static HostRepository Build() {

        Properties properties = new Properties();
        String path = ""+ HostRepositoryBuilder.class.getClassLoader().getResource("");
        String configFile = path.substring(6) + "storageConf.properties";
        try {
            properties.load(new FileInputStream(configFile));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        int storageType = Integer.parseInt(properties.getProperty("storageMethod"));

        HostRepository result;
        switch(storageType) {
            case 1:
                result = new HostDB();
                break;
            case 2:
                result = new HostTxt();
                break;
            default:
                result = null;
        }
        return result;
    }
}
