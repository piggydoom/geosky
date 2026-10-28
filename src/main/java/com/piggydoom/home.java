package com.piggydoom;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class home {

    @FXML
    public Button hbFlashcards;
    
    @FXML
    private void toGame(String gameFXML) throws IOException {
        App.setRoot(gameFXML);
    }

    
}
