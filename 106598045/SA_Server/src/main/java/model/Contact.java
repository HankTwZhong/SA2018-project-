package model;

import Notify.SendingEmail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Contact {
    private String name;
    private String email;
    private String method;
    private Map<String,ArrayList<String>> addressList;

    public Contact(final String contactName,final String email, final Map<String,ArrayList<String>> addressList) {
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

    public Map<String,ArrayList<String>> getAddress() {
        return addressList;
    }

    public void setAddress(String type,String address) {
        ArrayList<String> arrayList = this.addressList.get(type);
        arrayList.add(address);
        this.addressList.put(type,arrayList);
    }

}
