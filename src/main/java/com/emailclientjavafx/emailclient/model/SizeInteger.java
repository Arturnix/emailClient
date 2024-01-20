package com.emailclientjavafx.emailclient.model;

public class SizeInteger implements Comparable<SizeInteger>{

    //toString() method is called when I see data in table view
    private int size;

    public SizeInteger(int size) {
        this.size = size;
    }

    //so I need to overwrite toString() method
    //by default, size is sorted by string value not by size - use interface comparable. By default Integer class implements Comparable. So for SizeInteger I need to implelemnts Comparable interface.
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

    @Override
    public int compareTo(SizeInteger o) { //depend on sizes, comparison returns -1, 0, 1
        if(size > o.size) { //size - current size
            return 1;
        } else if(o.size > size) {
            return -1;
        } else {
            return 0;
        }
    }
}
