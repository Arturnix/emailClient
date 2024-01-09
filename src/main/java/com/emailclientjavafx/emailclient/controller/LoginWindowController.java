package com.emailclientjavafx.emailclient.controller;

import com.emailclientjavafx.emailclient.EmailManager;
import com.emailclientjavafx.emailclient.controller.services.LoginService;
import com.emailclientjavafx.emailclient.model.EmailAccount;
import com.emailclientjavafx.emailclient.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController {

    @FXML
    private TextField emailAddressField;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction() {

        //if I have validation I can create email account/login
        if(fieldsAreValid()) {
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText()); //getting login data from login window text fields
            LoginService loginService = new LoginService(emailAccount, emailManager);
            EmailLoginResult emailLoginResult = loginService.login();

            switch (emailLoginResult) {
                case SUCCESS:
                    System.out.println("Login successfull! " + emailAccount);
            }
        }
        System.out.println("LoginButtonAction");

        viewFactory.showMainWindow();
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

    private boolean fieldsAreValid() {

        if(emailAddressField.getText().isEmpty()) {
            errorLabel.setText("Please fill email");
            return false;
        }
        if(passwordField.getText().isEmpty()) {
            errorLabel.setText("Please fill password");
            return false;
        }

        return true;
    }
}
