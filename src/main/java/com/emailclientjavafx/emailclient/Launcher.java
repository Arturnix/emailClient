package com.emailclientjavafx.emailclient;

import com.emailclientjavafx.emailclient.view.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        ViewFactory viewFactory = new ViewFactory(new EmailManager());
        //viewFactory.showLoginWindow();
        viewFactory.showMainWindow();
        //viewFactory.showOptionsWindow();
        //viewFactory.updateStyles();
    }
}
