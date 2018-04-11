package Notify;

import Notify.SendingEmail;
import model.Host;
import model.IObserver;

public class EmailObserver implements IObserver {
    public void Update(Host host) {
        String msg = "host status change!!!  host name : "+host.getHostName()+", host ip : "+host.getHostIp()+",lastCheckTime : "+host.getLastCheck();
        for(int i =0;i< host.getContactList().size();i++){
            String contactEmail = host.getContactList().get(i).getEmail();
            SendingEmail s = new SendingEmail("gunchanatest@gmail.com", "0975200982","gunchanatest@gmail.com", contactEmail,"host status change!!!",msg);
            s.Sending();
        }
    }
}
