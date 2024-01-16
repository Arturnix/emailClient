package com.emailclientjavafx.emailclient;

import com.emailclientjavafx.emailclient.controller.services.FetchFoldersService;
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

        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
       //iterate through my folders
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }
}
