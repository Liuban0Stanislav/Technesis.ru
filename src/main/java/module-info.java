module org.lyuban.technesis {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.lyuban.technesis to javafx.fxml;
    exports org.lyuban.technesis;
}