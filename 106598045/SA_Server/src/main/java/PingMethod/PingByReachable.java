package PingMethod;
import Monitor.GetHostStatusCommand;

import java.net.InetAddress;

public class PingByReachable implements GetHostStatusCommand {
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
