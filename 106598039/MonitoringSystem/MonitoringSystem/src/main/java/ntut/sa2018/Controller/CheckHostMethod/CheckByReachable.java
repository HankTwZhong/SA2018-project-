package ntut.sa2018.Controller.CheckHostMethod;
import ntut.sa2018.UseCase.Interface.CheckHostMethodRepository;

import java.net.InetAddress;

public class CheckByReachable implements CheckHostMethodRepository {
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
