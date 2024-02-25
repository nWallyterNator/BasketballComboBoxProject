module com.example.basketballcomboboxproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.basketballcomboboxproject to javafx.fxml;
    exports com.example.basketballcomboboxproject;


    opens model to javafx.base;
    exports model;
}