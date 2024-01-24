package com.emailclientjavafx.emailclient.controller.services;

import com.emailclientjavafx.emailclient.model.EmailTreeItem;
import com.emailclientjavafx.emailclient.view.IconResolver;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import java.util.List;

public class FetchFoldersService extends Service<Void> { //as return type of Service class I can use void so it doest return anything

    private Store store; //starting point of getting emails; starting point for the logic
    private EmailTreeItem<String> foldersRoot;
    private List<Folder> folderList;

    private IconResolver iconResolver = new IconResolver();

    public FetchFoldersService(Store store, EmailTreeItem<String> foldersRoot, List<Folder> folderList) {
        this.store = store;
        this.foldersRoot = foldersRoot;
        this.folderList = folderList;
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

    //get list of folders from email account
    private void handleFolders(Folder[] folders, EmailTreeItem<String> foldersRoot) throws MessagingException {
        //iterate through the folders list
        for(Folder folder : folders) {
            folderList.add(folder); //to make dynamic action of updating messages list when new is added or removed
            //create email tree item from it
            EmailTreeItem<String> emailTreeItem = new EmailTreeItem<String>(folder.getName());
            //add icons to display for folders
            emailTreeItem.setGraphic(iconResolver.getIconForFolder(folder.getName()));
            //add it to the folder root
            foldersRoot.getChildren().add(emailTreeItem);
            //list all the folders from email account to be visibile in email tree view as expanded
            foldersRoot.setExpanded(true);
            fetchMessagesOnFolder(folder, emailTreeItem); //get email messages for each folder.
            addMessageListenerToFolder(folder, emailTreeItem);//add listener to update email list when emails are received while prgoram is running
            if(folder.getType() == Folder.HOLDS_FOLDERS) { //czy wewnatrz tego folderu sa inne foldery
                Folder[] subFolders = folder.list();
                handleFolders(subFolders, emailTreeItem); //recursive
            }
        }
    }

    private void addMessageListenerToFolder(Folder folder, EmailTreeItem<String> emailTreeItem) {
        folder.addMessageCountListener(new MessageCountListener() { //anonymous class with 2 methods
            @Override
            public void messagesAdded(MessageCountEvent messageCountEvent) { //I need to add folder updater service to make it work
               for(int i = 0; i < messageCountEvent.getMessages().length; i++) { //After 5000 ms of sleep for thread it can be received more than 1 new messages so I need to put them into list so I can iterate through it
                   try {
                       Message message = folder.getMessage(folder.getMessageCount() - i); //messages are in folder
                       emailTreeItem.addEmailToTop(message);
                   } catch (MessagingException e) {
                       e.printStackTrace();
                       throw new RuntimeException(e);
                   }

               }
            }

            @Override
            public void messagesRemoved(MessageCountEvent messageCountEvent) {
                System.out.println("Message removed event: " + messageCountEvent);
            }
        });
    }

    private void fetchMessagesOnFolder(Folder folder, EmailTreeItem<String> emailTreeItem) { //to get messages for folders, I will create separate service for each folder.

        Service fetchMessagesService = new Service() { //dont need argument here because it doesnt return anything
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception { //here I start write my getting email logic
                        if(folder.getType() != Folder.HOLDS_FOLDERS) {
                            folder.open(Folder.READ_WRITE); //permissions read write
                            int folderSize = folder.getMessageCount();
                            for(int i = folderSize; i > 0; i--) {
                                emailTreeItem.addEmail(folder.getMessage(i));
                            }
                        }
                        return null;
                    }
                };
            }
        };
        fetchMessagesService.start();
    }
}
