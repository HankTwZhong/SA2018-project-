package model;

import Command.GetHostStatusCommand;

public class Host {
    private String hostName;
    private String hostIp;
    private String status;
    private String lastCheck;
    private int checkMethod;
    //private String
    //private ArrayList<Contact> contactArrayList;

    public void setHostName(String name){
        this.hostName = name;
    }

    public String getHostName(){
        return this.hostName;
    }

    public void setStatus(String status){
        this.status = status;
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

/*
    public ArrayList<Contact> getContactArrayList() {
        return contactArrayList;
    }

    public void setContactArrayList(ArrayList<Contact> contactArrayList) {
        this.contactArrayList = contactArrayList;
    }*/
}
