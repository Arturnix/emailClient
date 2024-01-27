package com.emailclientjavafx.emailclient.controller;

import com.emailclientjavafx.emailclient.EmailManager;
import com.emailclientjavafx.emailclient.controller.services.MessageRendererService;
import com.emailclientjavafx.emailclient.model.EmailMessage;
import com.emailclientjavafx.emailclient.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends BaseController implements Initializable {

    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "/Pobrane";

    @FXML
    private Label attachmentLabel;

    @FXML
    private HBox hBoxDownloads;

    @FXML
    private Label senderLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private WebView webView;

    public EmailDetailsController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { //in this method everything happens - starting point for the logic

        EmailMessage emailMessage = emailManager.getSelectedMessage(); //this is current selected message
        subjectLabel.setText(emailMessage.getSubject());
        senderLabel.setText(emailMessage.getSender());
        loadAttachemnts(emailMessage);

        MessageRendererService messageRendererService = new MessageRendererService(webView.getEngine());
        messageRendererService.setEmailMessage(emailMessage);
        messageRendererService.restart();

    }

    private void loadAttachemnts(EmailMessage emailMessage) {
        if(emailMessage.hasAttachments()) {
            for(MimeBodyPart mimeBodyPart : emailMessage.getAttachmentsList()) {
                Button button = null;
                try {
                    button = new Button(mimeBodyPart.getFileName());
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                hBoxDownloads.getChildren().add(button);
            }
        } else {
            attachmentLabel.setText(""); //hide attchamnets field method if there is no attachment in email
        }
    }
}
