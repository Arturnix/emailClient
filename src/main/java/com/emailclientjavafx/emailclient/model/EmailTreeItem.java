package com.emailclientjavafx.emailclient.model;

import javafx.scene.control.TreeItem;

public class EmailTreeItem<String> extends TreeItem<String> {

    private String name;

    //I will use this tree item instead of regular tree item
    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
    }
}
