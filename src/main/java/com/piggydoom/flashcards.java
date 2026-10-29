package com.piggydoom;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class flashcards {

    @FXML
    private Button flashcardsNextB;
    @FXML
    private ImageView flashcardsIV;
    @FXML
    private void toHome() throws IOException {
        App.setRoot("home");
    }

       

    private void nextFlag(){
        // flashcardsIV.setImage(adF);
    }

    public void initialize(){
        flashcardsNextB.setOnAction(event -> {
            nextFlag();
        });
    }
}