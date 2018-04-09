package model;

import java.util.List;

public class Contact {
    private String method;
    private String address;

    Contact(final String method, final String address) {
        this.method = method;
        this.address = address;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
