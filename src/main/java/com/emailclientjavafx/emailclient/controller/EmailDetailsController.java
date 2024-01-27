package com.emailclientjavafx.emailclient.controller;

import com.emailclientjavafx.emailclient.EmailManager;
import com.emailclientjavafx.emailclient.controller.services.MessageRendererService;
import com.emailclientjavafx.emailclient.model.EmailMessage;
import com.emailclientjavafx.emailclient.view.ViewFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends BaseController implements Initializable {

    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.name") + "/Downloads";

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
        if (emailMessage.hasAttachments()) {
            for (MimeBodyPart mimeBodyPart : emailMessage.getAttachmentsList()) {
                AttachmentButton button = null;
                try {
                    button = new AttachmentButton(mimeBodyPart);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                hBoxDownloads.getChildren().add(button);
            }
        } else {
            attachmentLabel.setText(""); //hide attchamnets field method if there is no attachment in email
        }
    }

    private class AttachmentButton extends Button {

        private MimeBodyPart mimeBodyPart;
        private String downloadedFilePath;

        public AttachmentButton(MimeBodyPart mimeBodyPart) throws MessagingException {
            this.mimeBodyPart = mimeBodyPart;
            this.setText(mimeBodyPart.getFileName());
            this.downloadedFilePath = LOCATION_OF_DOWNLOADS + mimeBodyPart.getFileName();

            this.setOnAction(e -> downloadAttachment());
        }

        private void downloadAttachment() { //it can be long going task so it is good idea to put it into another thread

            colorBlue();
            Service service = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            mimeBodyPart.saveFile(downloadedFilePath);
                            return null;
                        }
                    };
                }
            };
            service.restart();
            service.setOnSucceeded(e-> {
                colorGreen();
                this.setOnAction(e2 -> {
                    File file = new File(downloadedFilePath);
                    Desktop desktop = Desktop.getDesktop();
                    if(file.exists()) {
                        try {
                            desktop.open(file);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            });
        }

        private void colorBlue() { //start downloading color button blue
            this.setStyle("-fx-background-color: Blue");
        }

        private void colorGreen() { //color button green when download is completed
            this.setStyle("-fx-background-color: Green");
        }
    }
}


