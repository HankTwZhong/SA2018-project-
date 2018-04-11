package model;

import Notify.SendingEmail;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private String name;
    private String email;
    private String method;
    private ArrayList<String> addressList;

    public Contact(final String contactName,final String email, final ArrayList<String> addressList) {
        this.name =contactName;
        this.email = email;
        this.addressList = addressList;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ArrayList<String> getAddress() {
        return addressList;
    }

    public void setAddress(String address) {
        this.addressList.add(address);
    }

}
