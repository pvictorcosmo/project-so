module com.so.projectso {
    requires javafx.controls;
    requires javafx.fxml;


    opens controllers to javafx.fxml;
    exports controllers;
    exports model;
    opens model to javafx.fxml;
    exports view;
    opens view to javafx.fxml;
}