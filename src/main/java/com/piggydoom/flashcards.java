package com.piggydoom;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class flashcards {

    @FXML
    private Button flashcardsNextB;
    @FXML
    private ImageView flashcardsIV;
    @FXML
    private TextFlow currentFCInfo;

    @FXML
    private void toHome() throws IOException {
        App.setRoot("home");
    }

    home.CorT currentFC;
    Integer revealed = 0;

    // efficient way to grab a complete random- grab a random NUMBER from the
    // totalCorTs, and find if it is higher or
    // lower than the size of the smth smths
    private void revealCurrentFCInfo() {
        revealed = 1;
        List<Text> info = List.of(new Text(currentFC.name), new Text("Capital - " + currentFC.capital));
        Text capital = new Text("Capital - " + currentFC.capital);

        currentFCInfo.getChildren().addAll(info);
        currentFCInfo.getChildren().add(capital);
    }

    private void nextFlag() {
        revealed = 0;
        currentFC = home.totalCorTs.get(ThreadLocalRandom.current().nextInt(0, home.totalCorTs.size() + 1));
        flashcardsIV.setImage(home.returnCorTImg(currentFC.index));

    }

    // public Image adF = new
    // Image(getClass().getResource("/com/piggydoom/flags/ad.png").toExternalForm());
    public void initialize() {
        flashcardsNextB.setOnAction(event -> {
            if (revealed == 0) {
                revealCurrentFCInfo();
            } else {
                nextFlag();
            }

        });
    }
}