module com.piggydoom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;

    opens com.piggydoom to javafx.fxml;
    exports com.piggydoom;
}
