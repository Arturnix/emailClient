package com.emailclientjavafx.emailclient.controller.persistence;

import java.io.Serializable;

public class ValidAccount implements Serializable { //it is marker interface, it doesnt have any methods. Indicates that this class should be serialized.

    private String address;
    private String password;

    public ValidAccount(String address, String password) {
        this.address = address;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
