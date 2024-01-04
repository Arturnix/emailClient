package com.emailclientjavafx.emailclient.view;

import com.emailclientjavafx.emailclient.EmailManager;

public class ViewFactory {

    private EmailManager emailManager;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
    }

    public void showLoginWindow() {
        System.out.println("Show login window called");
    }
}
