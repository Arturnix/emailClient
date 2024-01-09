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
        System.out.println("LoginButtonAction");

        //if I have validation I can create email account/login
        if(fieldsAreValid()) {
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText()); //getting login data from login window text fields
            LoginService loginService = new LoginService(emailAccount, emailManager); //create service
            loginService.start(); //start background task
            //make login window clickable when click login and do connection and login service in background
            loginService.setOnSucceeded(event -> { //lambda expression; called when succeeded(task state succeeded)

                EmailLoginResult emailLoginResult = loginService.getValue(); //get value from the service

                switch (emailLoginResult) {
                    case SUCCESS:
                        System.out.println("Login successfull! " + emailAccount);
                        viewFactory.showMainWindow();
                        Stage stage = (Stage) errorLabel.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        return;
                    case FAILED_BY_CREDENTIALS:
                        errorLabel.setText("invalid credentials!");
                        return;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        errorLabel.setText("unexpected error!");
                        return;
                    default:
                        return;
                }
            });



        }


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
