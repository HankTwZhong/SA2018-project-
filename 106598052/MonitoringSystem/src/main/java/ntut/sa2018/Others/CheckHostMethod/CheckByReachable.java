package ntut.sa2018.Others.CheckHostMethod;
import ntut.sa2018.Others.Interface.PingInterface;

import java.net.InetAddress;

public class CheckByReachable implements PingInterface {
    public String execute(String ip) {
        boolean reachable = false;
        String result;
        try {
            InetAddress address = InetAddress.getByName(ip);
            reachable = address.isReachable(100);
        }
        catch (Exception e) {
            System.out.println("failed to ping, error : " + e.toString());
        }
        result = reachable ? "OK" : "ERROR";
        return result;
    }
}
