module com.example.projetdejava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.projetdejava to javafx.fxml;
    exports com.example.projetdejava;
}