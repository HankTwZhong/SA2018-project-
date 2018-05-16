package ntut.sa2018.Domain.Host;

import ntut.sa2018.Domain.Contact.Contact;
import ntut.sa2018.Others.Publisher;


public class Host extends Publisher {
    private String hostName;
    private String hostIP;
    private String hostStatus;
    private String checkMethod;
    private int checkInterval;
    private Contact contact;
    private String lastCheck;

    public Host(HostBuilder.newInstance newInstance){
        this.hostName = newInstance.hostName;
        this.hostIP = newInstance.hostIP;
        this.checkMethod = newInstance.checkMethod;
        this.checkInterval = newInstance.checkInterval;
        this.contact = newInstance.contact;
        this.addSubscriber(this.contact);
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public String getHostStatus() {
        return hostStatus;
    }

    public void setHostStatus(String hostStatus) {
        if(this.hostStatus!=hostStatus){
            notifySubscribers();
        }
        this.hostStatus = hostStatus;
    }

    public String getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(String checkMethod) {
        this.checkMethod = checkMethod;
    }

    public int getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(int checkInterval) {
        this.checkInterval = checkInterval;
    }

    public void printHost(){
        System.out.println("hostName = " + hostName);
        System.out.println("hostIP = " + hostIP);
        System.out.println("checkMethod = " + checkMethod);
        System.out.println("checkInterval = " + checkInterval);
        contact.printContact();
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact){
        this.contact = contact;
    }

    public String getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(String lastCheck) {
        this.lastCheck = lastCheck;
    }

    @Override
    public String getInfo() {
        String info = "hostName = " + hostName + ", hostIP = " + hostIP;
        return info;
    }
}