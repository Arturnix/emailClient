package com.emailclientjavafx.emailclient.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import java.util.Date;

public class EmailMessage {

    private SimpleStringProperty subject; //because TableView supports SimpleViewProperty not objects like String.
    private SimpleStringProperty sender;
    private SimpleStringProperty recipient;
    private SimpleIntegerProperty size;
    private SimpleObjectProperty<Date> date;
    private boolean isRead;
    private Message message;

    //constructor takes String as an arguments but then it converts it into SimpleStringProperty
    public EmailMessage(String subject, String sender, String recipient, int size, Date date, boolean isRead, Message message) {
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recipient);
        this.size = new SimpleIntegerProperty(size);
        this.date = new SimpleObjectProperty<Date>(date);
        this.isRead = isRead;
        this.message = message;
    }

    //getters and setters also are made different
    //it need to be implemented in appropriate way (data structure) to be displayed in java FX
    public String getSubject() {
        return this.subject.get();
    }
    public String getSender() {
        return this.sender.get();
    }
    public String getRecipient() {
        return this.recipient.get();
    }
    public Integer getSize() {
        return this.size.get();
    }
    public Date getDate() {
        return this.date.get();
    }
    public boolean isRead() {
        return isRead;
    }
    public void setRead(boolean read) {
        isRead = read;
    }
    public Message getMessage() {
        return this.message;
    }
    //this data structure is ready to be displayed in java fx program
}