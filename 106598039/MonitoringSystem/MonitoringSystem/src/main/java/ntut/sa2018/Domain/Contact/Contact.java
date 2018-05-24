package ntut.sa2018.Domain.Contact;

import ntut.sa2018.UseCase.Publisher;
import ntut.sa2018.UseCase.Subscriber;

public class Contact extends Subscriber{
    private String contactName;
    private String email;
    private String lineaddress;
    private String facebookaddress;
    private String skypeaddress;
    private String otheraddress;
    private String otheraddress2;

    public Contact(ContactBuilder.newInstance newInstance){
        this.contactName = newInstance.contactName;
        this.email = newInstance.email;
        this.lineaddress = newInstance.lineaddress;
        this.facebookaddress = newInstance.facebookaddress;
        this.skypeaddress = newInstance.skypeaddress;
        this.otheraddress = newInstance.address;
        this.otheraddress2 = newInstance.address2;
    }

    public void printContact(){
        System.out.println("contactName = " + contactName);
        System.out.println("email = " + email);
        System.out.println("lineaddress = " + lineaddress);
        System.out.println("facebookaddress = " + facebookaddress);
        System.out.println("skypeaddress = " + skypeaddress);
        System.out.println("otheraddress = " + otheraddress);
        System.out.println("otheraddress2 = " + otheraddress2);
    }

    public String getContactName() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }

    public String getLineaddress() {
        return lineaddress;
    }

    public String getFacebookaddress() {
        return facebookaddress;
    }

    public String getSkypeaddress() {
        return skypeaddress;
    }

    public String getOtheraddress() {
        return otheraddress;
    }

    public String getOtheraddress2() {
        return otheraddress2;
    }

    @Override
    public void update(Publisher p) {
        System.out.println("Email = " + email + ", " + p.getInfo() + " the host has change status");
        if(!lineaddress.equals("null")){
            System.out.println("Line address = " + lineaddress + ", " + p.getInfo() + " the host has change status");
        }
        if(!facebookaddress.equals("null")){
            System.out.println("Facebook address = " + facebookaddress + ", " + p.getInfo() + " the host has change status");
        }
        if(!skypeaddress.equals("null")){
            System.out.println("Skype address = " + skypeaddress + ", " + p.getInfo() + " the host has change status");
        }
        if(!otheraddress.equals("null")){
            System.out.println("Other address = " + otheraddress + ", " + p.getInfo() + " the host has change status");
        }
        if(!otheraddress2.equals("null")){
            System.out.println("Other address 2 = " + otheraddress2 + ", " + p.getInfo() + " the host has change status");
        }
    }
}