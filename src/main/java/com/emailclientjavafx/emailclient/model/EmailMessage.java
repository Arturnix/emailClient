package com.emailclientjavafx.emailclient.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmailMessage {

    private SimpleStringProperty subject; //because TableView supports SimpleViewProperty not objects like String.
    private SimpleStringProperty sender;
    private SimpleStringProperty recipient;
    private SimpleObjectProperty<SizeInteger> size; //in < > I type what is the type of provided data/object
    private SimpleObjectProperty<Date> date;
    private boolean isRead;
    private Message message;
    private List<MimeBodyPart> attachmentList = new ArrayList<MimeBodyPart>();
    private boolean hasAttachmenst = false;

    //constructor takes String as an arguments but then it converts it into SimpleStringProperty
    public EmailMessage(String subject, String sender, String recipient, int size, Date date, boolean isRead, Message message) {
        this.subject = new SimpleStringProperty(subject);
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recipient);
        this.size = new SimpleObjectProperty<SizeInteger>(new SizeInteger(size)); //this is ObjectType and I have separate class for it and constructor so I need to pass it to the constructor in that way
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
    public SizeInteger getSize() {
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

    public void addAttachment(MimeBodyPart mbp) {
        hasAttachmenst = true;
        attachmentList.add(mbp);
    }
    //this data structure is ready to be displayed in java fx program
}