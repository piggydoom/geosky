package com.piggydoom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class quickname {

    @FXML
    private void toHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private Button beginRestartBtn;
    @FXML
    private Label scoreLbl;
    @FXML
    private Label timeLbl;
    @FXML
    private Label promptLbl;
    @FXML
    private TextField inputField;
    @FXML
    private Button submitBtn;
    @FXML
    private ChoiceBox modeSel;

    private static class prompt {
        private Integer index;
        private String type;
        private String lookFor;
        private String pTitleText;

        private prompt() {
        }

        private prompt(Integer index, String type, String lookFor, String pTitleText) {

            this.index = index;
            this.type = type;
            this.lookFor = lookFor;
            this.pTitleText = pTitleText;
        }
    }

    List<prompt> cityPrompts = new ArrayList<>();
    List<prompt> countryPrompts = new ArrayList<>();
    int startTime = 30;
    int score;
    int time;
    int mode = 1;
    prompt selPrompt;
    private ObservableList<String> denyList = FXCollections.observableArrayList();

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
        if (time > 0) {
            time -= 1;
            timeLbl.setText(String.valueOf(time));
        }
        ;
    }));

    public void initialize() {

        modeSel.setItems(FXCollections.observableArrayList("Countries", "Cities"));
        modeSel.setValue("Countries");

        // "type" MUST match the JSON parameter you are looking at, and "lookFor" MUST
        // equal the exact value you are looking for as that JSON parameter
        cityPrompts.add(new prompt(1, "continent", "Africa", "Cities in African Countries"));
        cityPrompts.add(new prompt(2, "continent", "Europe", "Cities in European Countries"));
        cityPrompts.add(new prompt(3, "continent", "Asia", "Cities in Asian Countries"));
        cityPrompts.add(new prompt(4, "continent", "NA", "Cities in North American Countries"));
        cityPrompts.add(new prompt(5, "continent", "SA", "Cities in South American Countries"));
        cityPrompts.add(new prompt(6, "continent", "Oceania", "Cities in Oceanian Countries"));
        countryPrompts.add(new prompt(0, "continent", "Africa", "African Countries"));
        countryPrompts.add(new prompt(0, "continent", "Asia", "Asian Countries"));

        beginRestartBtn.setOnAction(event -> {
            beginOrRestart();
        });
        submitBtn.setOnAction(event -> {
            submitAns();
        });

        timeline.setCycleCount(startTime * 10);
    }

    private void beginOrRestart() {
        System.out.println(modeSel.getValue());
        Random rand = new Random();
        denyList.clear();
        score = 0;
        if (modeSel.getValue().equals("Countries")) {
            selPrompt = countryPrompts.get(rand.nextInt(countryPrompts.size()));
        } else if (modeSel.getValue().equals("Cities")) {
            selPrompt = cityPrompts.get(rand.nextInt(cityPrompts.size()));
        }

        promptLbl.setText(selPrompt.pTitleText);
        System.out.println(selPrompt.pTitleText);
        time = startTime;
        timeline.play();
    }

    private void submitAns() {

        if (!denyList.contains(inputField.getText().toLowerCase())) {

            // countries
            if (modeSel.getValue().equals("Countries")) {
                switch (selPrompt.type) {
                    case "continent":
                        try {
                            String submittedCorTCont = (home.getCorTbyName(inputField.getText()).cont);
                        } catch (Exception e) {
                            System.out.println("failed");
                        }
                        if ((home.getCorTbyName(inputField.getText()).cont).equals(selPrompt.lookFor)) {
                            System.out.println("correct");
                            denyList.add(inputField.getText().toLowerCase());
                            scoreLbl.setText(String.valueOf(score += 1));
                        }
                }
                // cities
            } else if (modeSel.getValue().equals("Cities")) {

            }
            //
        } else{
            System.out.println("already done");
        }
        //

    }
}

//linked city500 - quickname working score, no repeat rule, verifying continents, ignore case, and further geodata