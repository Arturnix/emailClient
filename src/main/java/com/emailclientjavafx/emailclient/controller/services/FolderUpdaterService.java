package com.emailclientjavafx.emailclient.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import java.util.List;

public class FolderUpdaterService extends Service { //it will have list of folder for all accounts added to program

    private List<Folder> folderList;

    public FolderUpdaterService(List<Folder> folderList) {
        this.folderList = folderList;
    }

    //this service will start when program starts and it will never end. So I put it into infinite for loop
    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for(;;) {
                    try {
                        Thread.sleep(5000);
                        for(Folder folder : folderList) { //I do it for all the folders for all account added so I use it inside EmailManager class because it has access to all the folders in every account
                            if(folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()) {
                                folder.getMessageCount();
                            }
                        }

                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        };
    }
}
