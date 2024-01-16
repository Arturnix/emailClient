package com.emailclientjavafx.emailclient.controller.services;

import com.emailclientjavafx.emailclient.model.EmailTreeItem;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

public class FetchFoldersService extends Service<Void> { //as return type of Service class I can use void so it doest return anything

    private Store store; //starting point of getting emails; starting point for the logic
    private EmailTreeItem<String> foldersRoot;

    public FetchFoldersService(Store store, EmailTreeItem<String> foldersRoot) {
        this.store = store;
        this.foldersRoot = foldersRoot;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                fetchFolders();
                return null;
            }
        };
    }

    private void fetchFolders() throws MessagingException { //takes folders from this.store
        //I will take folders as an array
        Folder[] folders = store.getDefaultFolder().list(); //list my folders
        handleFolders(folders, foldersRoot);
    }

    private void handleFolders(Folder[] folders, EmailTreeItem<String> foldersRoot) throws MessagingException {
        //iterate through the folders list
        for(Folder folder : folders) {
            //create email tree item from it
            EmailTreeItem<String> emailTreeItem = new EmailTreeItem<String>(folder.getName());
            //add it to the folder root
            foldersRoot.getChildren().add(emailTreeItem);
            //list all the folders from email account to be visibile in email tree view as expanded
            foldersRoot.setExpanded(true);
            if(folder.getType() == Folder.HOLDS_FOLDERS) { //czy wewnatrz tego folderu sa inne foldery
                Folder[] subFolders = folder.list();
                handleFolders(subFolders, emailTreeItem); //recursive
            }
        }
    }


}
