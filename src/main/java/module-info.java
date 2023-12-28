module com.emailclientjavafx.emailclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.emailclientjavafx.emailclient to javafx.fxml;
    exports com.emailclientjavafx.emailclient;
}