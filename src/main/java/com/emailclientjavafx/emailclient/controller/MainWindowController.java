package com.emailclientjavafx.emailclient.controller;

import com.emailclientjavafx.emailclient.EmailManager;
import com.emailclientjavafx.emailclient.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private WebView emailWebView;

    @FXML
    private TableView<?> emailsTableView; //so to display email messages I need data structure which is supported by table view to display them correctly. Java class bean - holds data that will be displayed.
    //TableView supports objects properties instead of objects. Instead of String type I use SimpleStringProperty.
    @FXML
    private TreeView<String> emailsTreeView;

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    // when I add new element on a layout in scene builder I need add it to the controller to make this item operable.

    @FXML
    void optionsAction() {
        viewFactory.showOptionsWindow();
    }

    @FXML
    void addAccountAction() {
        viewFactory.showLoginWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpEmailsTreeView();
    }

    private void setUpEmailsTreeView() {
        emailsTreeView.setRoot(emailManager.getFoldersRoot());
        emailsTreeView.setShowRoot(false); //hide this root
    }



}
