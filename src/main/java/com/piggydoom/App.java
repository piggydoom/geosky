package com.piggydoom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}

// to consider; do i import all at once or on the spot?

    // two or maybe more custom class arrays for countries and territories as to
    // have specialised stats, so you dont have "date of independence" for a state
    // that isnt independant
    // this immediantly makes sorting through single categories easier.
    // to give an equal chance for multi-category, either literally pick a random
    // one of the files from the flags folder, or
    // have an ultra-simple array of JUST the country/region indexs and pick from
    // that. or maybe even just pick a random number
    // from 1-however many files there are and include a total index value that is
    // consistent among both or more custom class arrays