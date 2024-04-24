module org.lyuban.technesis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.lyuban.technesis to javafx.fxml;
    exports org.lyuban.technesis;
    exports org.lyuban.technesis.DataBaceConnection;
    opens org.lyuban.technesis.DataBaceConnection to javafx.fxml;
    exports org.lyuban.technesis.controllers;
    opens org.lyuban.technesis.controllers to javafx.fxml;
    exports org.lyuban.technesis.service;
    opens org.lyuban.technesis.service to javafx.fxml;
}