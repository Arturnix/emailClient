package com.emailclientjavafx.emailclient.controller;

import com.emailclientjavafx.emailclient.EmailManager;
import com.emailclientjavafx.emailclient.view.ViewFactory;

public abstract class BaseController {

    private EmailManager emailManager;
    private ViewFactory viewFactory;
    private String fxmlName;

    public BaseController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }
}
