module com.example.minesweepr {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.minesweepr to javafx.fxml;
    exports com.example.minesweepr;
}