package Notify;

import model.Host;
import model.IObserver;

import java.util.ArrayList;
import java.util.Map;

public class SkypeObserver  implements IObserver {
    public void Update(Host host) {
        //String msg = "host status change!!!  host name : "+host.getHostName()+", host ip : "+host.getHostIp()+",lastCheckTime : "+host.getLastCheck();
        for(int i =0;i< host.getContactList().size();i++){
            Map<String,ArrayList<String>> addressList = host.getContactList().get(i).getAddress();
            ArrayList<String> addressForFaceBook = addressList.get("Skype");
            for(int j=0;j<addressForFaceBook.size();j++) {
                System.out.println("send status change msg to " + host.getContactList().get(j).getName() + " Skype address =>" + addressForFaceBook.get(j));
            }
        }
    }
}
