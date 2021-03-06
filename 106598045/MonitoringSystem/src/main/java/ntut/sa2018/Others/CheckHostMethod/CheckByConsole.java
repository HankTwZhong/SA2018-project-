package ntut.sa2018.Others.CheckHostMethod;

import ntut.sa2018.Others.Interface.PingInterface;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CheckByConsole implements PingInterface {
    public String execute(String ip) {
        boolean status = true;
        String result;
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
        result = status ? "OK" : "ERROR";
        return result;
    }
}