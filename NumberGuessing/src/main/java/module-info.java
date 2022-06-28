module com.example.numberguessing {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.numberguessing to javafx.fxml;
    exports com.example.numberguessing;
}