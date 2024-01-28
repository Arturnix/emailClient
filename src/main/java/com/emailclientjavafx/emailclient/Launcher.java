package com.emailclientjavafx.emailclient;

import com.emailclientjavafx.emailclient.controller.persistence.PersistenceAccess;
import com.emailclientjavafx.emailclient.controller.persistence.ValidAccount;
import com.emailclientjavafx.emailclient.controller.services.LoginService;
import com.emailclientjavafx.emailclient.model.EmailAccount;
import com.emailclientjavafx.emailclient.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private PersistenceAccess persistenceAccess = new PersistenceAccess();
    private EmailManager emailManager = new EmailManager();

    @Override
    public void start(Stage primaryStage) throws IOException {

        ViewFactory viewFactory = new ViewFactory(emailManager);
        List<ValidAccount> validAccountList = persistenceAccess.loadFromPersistence();

        if(validAccountList.size() > 0) {
            viewFactory.showMainWindow();
            for(ValidAccount validAccount : validAccountList) {
                EmailAccount emailAccount = new EmailAccount(validAccount.getAddress(), validAccount.getPassword());
                LoginService loginService = new LoginService(emailAccount, emailManager);
                loginService.start();
            }
        } else {
            viewFactory.showLoginWindow();
        }
    }

    @Override
    public void stop() throws Exception {

        List<ValidAccount> validAccountList = new ArrayList<ValidAccount>(); //valid account are accessable in EmailManager

        for(EmailAccount emailAccount : emailManager.getEmailAccounts()) { //this list of email accounts is populated when I have successful login
            validAccountList.add(new ValidAccount(emailAccount.getAddress(), emailAccount.getPassword()));
        }

        persistenceAccess.saveToPersistence(validAccountList);
    }
}
