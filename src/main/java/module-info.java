module com.example.javafx2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.xml;

    opens com.example.javafx2 to javafx.fxml;
    exports com.example.javafx2;
}