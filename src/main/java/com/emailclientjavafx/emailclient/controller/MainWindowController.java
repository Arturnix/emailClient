package com.emailclientjavafx.emailclient.controller;

import com.emailclientjavafx.emailclient.EmailManager;
import com.emailclientjavafx.emailclient.controller.services.MessageRendererService;
import com.emailclientjavafx.emailclient.model.EmailMessage;
import com.emailclientjavafx.emailclient.model.EmailTreeItem;
import com.emailclientjavafx.emailclient.model.SizeInteger;
import com.emailclientjavafx.emailclient.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private WebView emailWebView;

    @FXML
    private TableView<EmailMessage> emailsTableView; //so to display email messages I need data structure which is supported by table view to display them correctly. Java class bean - holds data that will be displayed.
    //TableView supports objects properties instead of objects. Instead of String type I use SimpleStringProperty.
    @FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private TableColumn<EmailMessage, String> senderCol; //1st argument overall data structure - EmailMessage; 2nd argument type of this data value - String, Integer...
    @FXML
    private TableColumn<EmailMessage, String> subjectCol;
    @FXML
    private TableColumn<EmailMessage, String> recipientCol;
    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeCol;
    @FXML
    private TableColumn<EmailMessage, Date> dateCol;

    private MessageRendererService messageRendererService;

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
        setUpEmailsTableView();
        setUpFolderSelection();
        setUpBoldRows();
        setUpMessageRendererService();
        setUpMessageSelection();
    }

    private void setUpMessageSelection() {
        emailsTableView.setOnMouseClicked(event-> {
            EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();
            if(emailMessage != null) {
                emailManager.setSelectedMessage(emailMessage); //selected message is this message, the one I selected. Inform application that this message is selected
                if(!emailMessage.isRead()) {
                    emailManager.setRead();
                }
                emailManager.setSelectedMessage(emailMessage); //inform application that this message is selected
                messageRendererService.setEmailMessage(emailMessage);
                messageRendererService.restart(); //use restart method because start method can be used only once.
                //so if I call start, when I call this method twice there will be an error
            }
        });
    }

    private void setUpMessageRendererService() {
       messageRendererService = new MessageRendererService(emailWebView.getEngine()); //only initializing
    }

    private void setUpBoldRows() {

        emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> param) {
                return new TableRow<EmailMessage>() { //anonymous function
                    @Override
                    protected void updateItem(EmailMessage item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null) {
                            if(item.isRead()) {
                                setStyle("");
                            } else {
                                setStyle("-fx-font-weight: bold");
                            }
                        }
                    }
                };
            }
        });
    }

    private void setUpFolderSelection() { //from which folder - selected - messages will be displayed in table view

        emailsTreeView.setOnMouseClicked(e->{ //e-> lambda
            EmailTreeItem<String> item = (EmailTreeItem<String>)emailsTreeView.getSelectionModel().getSelectedItem(); //what happen when I click on the link in tree view

            if(item != null) {//not to have nullPointerException if I click outside the link to select folder
                emailManager.setSelcetedFolder(item);
                emailsTableView.setItems(item.getEmailMessages()); //get email messages to display in table view from selected folder
            }

        });

    }

    private void setUpEmailsTableView() { //for each of data column in table view (subject, recipient...) I need cell value factory

        senderCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("sender")));
        subjectCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("subject")));
        recipientCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("recipient")));
        sizeCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, SizeInteger>("size")));
        dateCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, Date>("date")));
    }

    private void setUpEmailsTreeView() {
        emailsTreeView.setRoot(emailManager.getFoldersRoot());
        emailsTreeView.setShowRoot(false); //hide this root
    }



}
