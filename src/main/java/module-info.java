module com.example.javafx2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires proj4j;


    opens com.example.javafx2 to javafx.fxml;
    exports com.example.javafx2;
}