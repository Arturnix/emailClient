package com.emailclientjavafx.emailclient.model;

public class SizeInteger {

    //toString() method is called when I see data in table view
    private int size;

    public SizeInteger(int size) {
        this.size = size;
    }

    //so I need to overwrite toString() method

    @Override
    public String toString() {
        if(size <= 0) {
            return "0";
        } else if(size < 1024) {
            return size + " B";
        } else if(size < 1048576) {
            return size / 1024 + " kB";
        } else {
            return size / 1048576 + " MB";
        }
    }
}
