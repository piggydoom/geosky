package com.piggydoom;

import java.io.IOException;
import javafx.fxml.FXML;

public class flashcards {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("home");
    }
}