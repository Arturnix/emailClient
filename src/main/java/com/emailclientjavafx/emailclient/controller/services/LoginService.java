package com.emailclientjavafx.emailclient.controller.services;
//this package stores logic of email operations

import com.emailclientjavafx.emailclient.EmailManager;
import com.emailclientjavafx.emailclient.model.EmailAccount;

public class LoginService {

    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    public void login() { //here will be only 1 method. Login method. But I must indicate the result of the login operation! Use enum to indicate login result.


    }
}
