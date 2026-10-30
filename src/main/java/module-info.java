module com.piggydoom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    opens com.piggydoom to javafx.fxml;
    exports com.piggydoom;
}
