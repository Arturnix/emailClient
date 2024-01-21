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

    public ObservableList<EmailMessage> getEmailMessages() {
        return emailMessages;
    }

    //method to get messages and use in java fx program.
    public void addEmail(Message message) throws MessagingException {
        EmailMessage emailMessage = fetchMessage(message);
        emailMessages.add(emailMessage);
    }

    public void addEmailToTop(Message message) throws MessagingException{
        EmailMessage emailMessage = fetchMessage(message);
        emailMessages.add(0, emailMessage); //index 0 means the top of the list
    }

    private EmailMessage fetchMessage(Message message) throws MessagingException {
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
        if(!messageIsRead) {
            incrementMessagesCount();
        }
        return emailMessage;
    }

    public void incrementMessagesCount() {
        unreadMessagesCount++; //everytime it increments I need to update name
        updateName();
    }

    public void decrementMessagesCount() {
        unreadMessagesCount--; //everytime it decrements I need to update name
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
