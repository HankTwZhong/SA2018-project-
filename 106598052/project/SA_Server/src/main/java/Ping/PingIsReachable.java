package Ping;

import java.net.InetAddress;

import Repository.PingRepository;

public class PingIsReachable implements PingRepository {
	public boolean ping(String ip){
        boolean reachable = false;
        try {
            InetAddress address = InetAddress.getByName(ip);
            reachable = address.isReachable(100);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return reachable;
    }
}
