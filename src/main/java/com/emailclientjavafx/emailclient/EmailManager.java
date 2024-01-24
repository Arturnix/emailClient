package com.emailclientjavafx.emailclient;

import com.emailclientjavafx.emailclient.controller.services.FetchFoldersService;
import com.emailclientjavafx.emailclient.controller.services.FolderUpdaterService;
import com.emailclientjavafx.emailclient.model.EmailAccount;
import com.emailclientjavafx.emailclient.model.EmailMessage;
import com.emailclientjavafx.emailclient.model.EmailTreeItem;
import com.emailclientjavafx.emailclient.view.IconResolver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    //EmailManager must know which folder and which message is selected to mark it as read and update unread messages counter in selected folder
    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selcetedFolder;

    private ObservableList<EmailAccount> emailAccounts = FXCollections.observableArrayList();

    private IconResolver iconResolver = new IconResolver();

    public ObservableList<EmailAccount> getEmailAccounts() {
        return emailAccounts;
    }

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailTreeItem<String> getSelcetedFolder() {
        return selcetedFolder;
    }

    public void setSelcetedFolder(EmailTreeItem<String> selcetedFolder) {
        this.selcetedFolder = selcetedFolder;
    }

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

        emailAccounts.add(emailAccount);
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        treeItem.setGraphic(iconResolver.getIconForFolder(emailAccount.getAddress()));
       //iterate through my folders
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem, folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }

    public void setRead() {
        try {
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
            selcetedFolder.decrementMessagesCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUnread() {
        try {
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false); //set to false also on email server side
            selcetedFolder.incrementMessagesCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSelectedMessage() {
        try {
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true); //It says to server, yes - true, my message (selected) was deleted
            selcetedFolder.getEmailMessages().remove(selectedMessage); //remove deleted message from the folder
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
