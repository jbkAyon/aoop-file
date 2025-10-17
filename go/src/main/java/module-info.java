module com.example.go {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.go to javafx.fxml;
    exports com.example.go;
}