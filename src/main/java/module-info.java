module com.emailclientjavafx.emailclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.emailclientjavafx.emailclient to javafx.fxml;
    exports com.emailclientjavafx.emailclient;
    exports com.emailclientjavafx.emailclient.controller;
    opens com.emailclientjavafx.emailclient.controller to javafx.fxml;
}