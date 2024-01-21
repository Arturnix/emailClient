package com.emailclientjavafx.emailclient;

import com.emailclientjavafx.emailclient.controller.services.FetchFoldersService;
import com.emailclientjavafx.emailclient.controller.services.FolderUpdaterService;
import com.emailclientjavafx.emailclient.model.EmailAccount;
import com.emailclientjavafx.emailclient.model.EmailTreeItem;
import javafx.scene.control.TreeItem;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    private FolderUpdaterService folderUpdaterService;

    //Folder handling:
    //root

    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>(""); //no value, it is just a placeholder

    //pass it to the main window controller
    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    private List<Folder> folderList = new ArrayList<Folder>();

    public List<Folder> getFolderList() {
        return this.folderList;
    }

    public EmailManager() {
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start(); //it will start once with EmailManager and never ends so the list of added and removed messages will be up to date. Not only when the program start and fetches email from server.
    }

    public void addEmailAccount(EmailAccount emailAccount) {

        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
       //iterate through my folders
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem, folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }
}
