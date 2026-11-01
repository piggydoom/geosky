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

// NOTES

// "type"
// 1- country, recognised by the U.N
// 2- territory, autonomous or not
// 3- disputed, overly conflicting classifications among organizations 
// 4- other, really just antarctica

// current decision and plan as for the sorting----
// a) pre-cached lists. make 3 lists, 1 for type 1, 2 for type 2, and 3 for
// total. i will simplify the types as to
// only need the 3, ie. type 2, 3, and 4, will be merged into territories/other.
// if one is really indecisive just
// make it a setting like a lazy cunt. note that the type merging is purely for
// the sorting side, the JSON file will
// still have type 2,3,4 as seperates, but the lists will be simplified to (1)
// and (2/3/4).