module com.example.desktop {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    opens com.example.desktop to javafx.fxml;
    exports com.example.desktop;
}