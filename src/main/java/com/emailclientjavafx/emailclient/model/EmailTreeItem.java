package com.emailclientjavafx.emailclient.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailTreeItem<String> extends TreeItem<String> {

    private String name;
    private ObservableList<EmailMessage> emailMessages;
    private int unreadMessagesCount; //indicates number of unread messages. To display number of unread messages in folders.

    //I will use this tree item instead of regular tree item
    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
        this.emailMessages = FXCollections.observableArrayList(); //ObservableList is an interface so in the constructor I need helper class FXCollections. It generates list.
    }

    //method to get messages and use in java fx program.
    public void addEmail(Message message) throws MessagingException {
        boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
        //construct email message
        EmailMessage emailMessage = new EmailMessage(
                message.getSubject(),
                message.getFrom()[0].toString(),
                message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                message.getSize(),
                message.getSentDate(),
                messageIsRead,
                message
        );
        emailMessages.add(emailMessage);
        if(!messageIsRead) {
            incrementMessagesCount();
        }
        System.out.println("added to " + name + " " + message.getSubject());
    }

    public void incrementMessagesCount() {
        unreadMessagesCount++; //everytime it increments I need to update name
        updateName();
    }

    private void updateName() {
        if(unreadMessagesCount > 0) {
            this.setValue((String)(name + "(" + unreadMessagesCount + ")"));
        } else {
            this.setValue(name);
        }
    }

}
