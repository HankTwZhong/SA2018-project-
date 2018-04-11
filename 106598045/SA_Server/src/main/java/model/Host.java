package model;

import Command.GetHostStatusCommand;
import Notify.SendingEmail;

import java.util.ArrayList;
import java.util.List;

public class Host {
    private String hostName;
    private String hostIp;
    private String status;
    private String lastCheck;
    private int checkMethod;

    private ArrayList<Contact> contactArrayList;

    public Host(){
        contactArrayList = new ArrayList<Contact>();
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

    public void setContact(String contactName,String email, ArrayList<String> contactAddressList){
        Contact c = new Contact(contactName,email, contactAddressList);
        contactArrayList.add(c);
    }

    public ArrayList<Contact> getContactList(ArrayList<Contact> list){
        return contactArrayList;
    }

    public void setContactList(ArrayList<Contact> list){
        this.contactArrayList  = list;
    }

    public ArrayList<Contact> getContactList(){
        return this.contactArrayList;
    }

    public void printAllContact(){
        for(int i = 0;i < this.contactArrayList.size();i++){
            System.out.println("--------------第"+i+"位---------------");
            System.out.println(this.contactArrayList.get(i).getName());
            System.out.println(this.contactArrayList.get(i).getEmail());
            System.out.println(this.contactArrayList.get(i).getMethod());
            System.out.println(this.contactArrayList.get(i).getAddress());
        }
    }

    public void notifySubscriber(){
        IObserver iObserver = new EmailObserver();
        iObserver.Update(this);
    }
}
