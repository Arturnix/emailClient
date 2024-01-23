package com.emailclientjavafx.emailclient.model;

import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

//manage email actions
public class EmailAccount {

    private String address;
    private String password;
    private Properties properties;
    private Store store; //storing, sending and retrieving messages
    private Session session;

    public EmailAccount(String address, String password) {
        this.address = address;
        this.password = password;
        properties = new Properties(); //properties take from email provider

        properties.put("incomingHost", "imap.poczta.onet.pl"); //host to which will be connected my client; imap protocol - sending emails
        properties.put("mail.store.protocol", "imap"); //imaps
        properties.put("mail.transport.protocol", "smtps"); //smtp(s) s as secure
        properties.put("mail.smtps.host", "smtp.poczta.onet.pl");
        properties.put("mail.smtps.auth", "true");
        properties.put("outgoingHost", "smtp.poczta.onet.pl");
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return address;
    }
}
