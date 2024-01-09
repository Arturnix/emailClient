package com.emailclientjavafx.emailclient.controller.services;
//this package stores logic of email operations

import com.emailclientjavafx.emailclient.EmailManager;
import com.emailclientjavafx.emailclient.controller.EmailLoginResult;
import com.emailclientjavafx.emailclient.model.EmailAccount;

import javax.mail.*;

public class LoginService {

    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    public EmailLoginResult login() { //here will be only 1 method. Login method. But I must indicate the result of the login operation! Use enum to indicate login result.

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount.getAddress(), emailAccount.getPassword());
            }
        };

        //from this point of program flow a lot of things can go wrong, so use try catch block
        try {
            //access the session
            Session session = Session.getInstance(emailAccount.getProperties(), authenticator);
            Store store = session.getStore("imaps");
            //connect with this store
            store.connect(emailAccount.getProperties().getProperty("incomingHost"),
                    emailAccount.getAddress(),
                    emailAccount.getPassword()); //put incoming host here
            emailAccount.setStore(store); //using to get emails

        } catch(javax.mail.NoSuchProviderException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_NETWORK;
        } catch(AuthenticationFailedException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_CREDENTIALS;
        } catch(MessagingException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
        } catch(Exception e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
        }

        return EmailLoginResult.SUCCESS;

    }
}
