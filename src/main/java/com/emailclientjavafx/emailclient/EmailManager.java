package com.emailclientjavafx.emailclient;

import com.emailclientjavafx.emailclient.model.EmailAccount;
import com.emailclientjavafx.emailclient.model.EmailTreeItem;
import javafx.scene.control.TreeItem;

public class EmailManager {

    //Folder handling:
    //root

    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>(""); //no value, it is just a placeholder


    //pass it to the main window controller
    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount) {

        TreeItem<String> treeItem = new TreeItem<String>(emailAccount.getAddress());
        treeItem.setExpanded(true);
            treeItem.getChildren().add(new TreeItem<String>("INBOX"));
            treeItem.getChildren().add(new TreeItem<String>("Sent"));
            treeItem.getChildren().add(new TreeItem<String>("Folder1"));
            treeItem.getChildren().add(new TreeItem<String>("Spam"));
        foldersRoot.getChildren().add(treeItem);
    }
}
