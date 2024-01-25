package com.emailclientjavafx.emailclient.controller.services;

import com.emailclientjavafx.emailclient.model.EmailMessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;

public class MessageRendererService extends Service {

    private EmailMessage emailMessage; //email message to vizualize

    //web engine to vizualize messages. I get it from WebView of WindowController
    private WebEngine webEngine; //web engine from webView
    private StringBuffer stringBuffer; //holds the content which will be rendered by the web engine. When I load message content I put it in this stringBuffer.
    //and then the webEngine will load stringBuffer.

    public MessageRendererService(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.stringBuffer = new StringBuffer();
        this.setOnSucceeded(event-> { //lambda
            displayMessage();
        });
    }

    public void setEmailMessage(EmailMessage emailMessage) {
        this.emailMessage = emailMessage;
    }

    //call this method while the service is finished
    private void displayMessage() {
        webEngine.loadContent(stringBuffer.toString());
    }
    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    loadMessage(); //this is long running task - it takes a long time before view will be updated.
                    //start this service and when it is done display the message.
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    //load the message inside stringBuffer
    private void loadMessage() throws MessagingException, IOException {
        stringBuffer.setLength(0); // clears stringBuffer because this method will be called multiple times
        Message message = emailMessage.getMessage();
        String contentType = message.getContentType(); //can be simple or multipart. Create private method below to check is it simple type and second to check is it multipart type.
        if(isSimpleType(contentType)) {
            stringBuffer.append(message.getContent().toString());
        } else if(isMultipartType(contentType)) {
            //get multipart from message content
            Multipart multipart = (Multipart) message.getContent(); //cast because it returns an object
            //it contains array so I need to iterate through this object
            loadMultipart(multipart, stringBuffer);
        }
    }

    private void loadMultipart(Multipart multipart, StringBuffer stringBuffer) throws MessagingException, IOException {
        for(int i = multipart.getCount()-1; i >= 0; i--) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            String bodyPartContentType = bodyPart.getContentType();
            if(isSimpleType(bodyPartContentType)) {
                stringBuffer.append(bodyPart.getContent().toString());
            } else if(isMultipartType(bodyPartContentType)) { //recursion if there is miltipart i multipart
                Multipart multipart2 = (Multipart) bodyPart.getContent(); //Cast because there is no generic java mail implementation
                loadMultipart(multipart2, stringBuffer); //recursion
            }
        }
    }

    private boolean isSimpleType(String contentType) {
        if(contentType.contains("TEXT/HTML") ||
        //contentType.contains("mixed") ||
        contentType.contains("text")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isMultipartType(String contentType) {
        if(contentType.contains("multipart")) {
            return true;
        } else {
            return false;
        }
    }
}
