package Monitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.charset.Charset;

public class GetHostStatusCommand {

    public static String execute(String ip, int getMethod) {
        String status = "ERROR";
        switch(getMethod) {
            case 1:
                status = pingByReachable(ip) ? "OK" : "ERROR";
                break;
            case 2:
                status = pingByConsole(ip) ? "OK" : "ERROR";
        }
        return status;
    }

	private static boolean pingByReachable(String ip) {
        boolean reachable = false;
        try {
            InetAddress address = InetAddress.getByName(ip);
            reachable = address.isReachable(100);
        } 
        catch (Exception e) {
            System.out.println("failed to ping, error : " + e.toString());
        }
        return reachable;
    }

    private static boolean pingByConsole(String ip) {
        boolean status = true;
        String line = null;
        try {
            Process pro = Runtime.getRuntime().exec("ping " + ip);
            BufferedReader buf = new BufferedReader(new InputStreamReader(
                    pro.getInputStream(), "Big5"));
            while ((line = buf.readLine()) != null){
                if(line.contains("100% 遺失"))
                    status = false;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return status;
    }
}