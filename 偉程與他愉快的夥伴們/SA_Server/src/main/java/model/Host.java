package model;

import Notify.*;

import java.util.ArrayList;
import java.util.Map;

public class Host {
    private String hostName;
    private String hostIp;
    private String status;
    private String lastCheck;
    private int checkMethod;

    private ArrayList<Contact> contactArrayList;
    private ArrayList<IObserver> observerList;

    public Host(){
        contactArrayList = new ArrayList<Contact>();
        observerList = new ArrayList<IObserver>();
    }

    public void setHostName(String name){
        this.hostName = name;
    }

    public String getHostName(){
        return this.hostName;
    }

    public void setStatus(String status){
        if(!status.equals(this.getStatus()) && this.getStatus() != null){
            this.status = status;
            notifySubscriber();
        }else{
            this.status = status;
        }
    }

    public String getStatus(){
        return this.status;
    }

    public void setHostIp(String ip){
        this.hostIp = ip;
    }

    public String getHostIp(){
        return this.hostIp;
    }

    public void setLastCheck(String lastCheck){
        this.lastCheck = lastCheck;
    }

    public String getLastCheck(){
        return this.lastCheck;
    }

    public void setCheckMethod(int checkMethod) { this.checkMethod = checkMethod; }

    public int getCheckMethod() { return this.checkMethod; }

    public ArrayList<Contact> getContactList(ArrayList<Contact> list){
        return contactArrayList;
    }

    public void setContactList(ArrayList<Contact> list){
        this.contactArrayList  = list;
        IObserver emailObserver = new EmailObserver();
        this.regster(emailObserver);
        IObserver fbObserver = new FBObserver();
        this.regster(fbObserver);
        IObserver skObserver = new SkypeObserver();
        this.regster(skObserver);
    }

    public ArrayList<Contact> getContactList(){
        return this.contactArrayList;
    }

    public void regster(IObserver observer){
        observerList.add(observer);
    }

    public void removeObserver(IObserver observer){
        observerList.remove(observer);
    }

    public void notifySubscriber(){
        if(observerList != null) {
            for (int i = 0; i < observerList.size(); i++) {
                observerList.get(i).Update(this);
            }
        }
    }

}
