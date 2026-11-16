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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
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
    @FXML
    private TilePane viewPane;
    @FXML
    private Label failedLbl;
    @FXML
    private Button seeMissedAndGotToggleBtn;

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
    List<Node> changeWhenWrongAndRightNodes = new ArrayList();
    int startTime = 30;
    int score;
    int time;
    boolean active = false;
    int mode = 1;
    int endViewMode = 1; // 1 = what was gotten, 2 = what was missed
    prompt selPrompt;
    private ObservableList<String> sampleSpace = FXCollections.observableArrayList();
    private ObservableList<String> gotList = FXCollections.observableArrayList();

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
        if (time > 0) {
            time -= 1;
            timeLbl.setText(String.valueOf(time));

        } else {
            negativeResponse(1, null);
        }
        ;
    }));

    public void initialize() {
        seeMissedAndGotToggleBtn.setVisible(false);
        modeSel.setItems(FXCollections.observableArrayList("Countries", "Cities"));
        modeSel.setValue("Countries");

        // "type" MUST match the JSON parameter you are looking at, and "lookFor" MUST
        // equal the exact value you are looking for as that JSON parameter
        // cityPrompts.add(new prompt(1, "continent", "Africa", "Cities in African
        // Countries"));
        cityPrompts.add(new prompt(2, "continent", "Europe", "Cities in European Countries"));
        cityPrompts.add(new prompt(3, "continent", "Asia", "Cities in Asian Countries"));
        cityPrompts.add(new prompt(4, "continent", "NA", "Cities in North American Countries"));
        cityPrompts.add(new prompt(5, "continent", "SA", "Cities in South American Countries"));
        cityPrompts.add(new prompt(6, "continent", "Oceania", "Cities in Oceanian Countries"));
        cityPrompts.add(new prompt(0, "country", "US", "Cities in the U.S.A"));
        countryPrompts.add(new prompt(0, "continent", "Africa", "African Countries"));
        countryPrompts.add(new prompt(0, "continent", "Asia", "Asian Countries"));

        changeWhenWrongAndRightNodes.add(beginRestartBtn);
        changeWhenWrongAndRightNodes.add(submitBtn);
        changeWhenWrongAndRightNodes.add(timeLbl);
        changeWhenWrongAndRightNodes.add(scoreLbl);

        beginRestartBtn.setOnAction(event -> {
            beginOrRestart();
        });
        submitBtn.setOnAction(event -> {
            if (active) {
                submitAns();
            }
        });

        seeMissedAndGotToggleBtn.setOnAction(event -> {
            if (endViewMode == 1) {
                endViewMode = 2;
                seeMissedAndGotToggleBtn.getStyleClass().add("buttonStyle2-1");
                seeMissedAndGotToggleBtn.setText("See gotten");
            } else {
                endViewMode = 1;
                seeMissedAndGotToggleBtn.getStyleClass().clear();
                seeMissedAndGotToggleBtn.getStyleClass().add("buttonStyle2");
                seeMissedAndGotToggleBtn.setText("See missed");

                for (String got : gotList) {
                    Label insert = new Label(got);
                    insert.getStyleClass().add("generalBox2");
                    viewPane.getChildren().add(insert);
                }

            }
        });

        timeline.setCycleCount(startTime * 10);

    }

    private void beginOrRestart() {

        Random rand = new Random();
        gotList.clear();
        viewPane.getChildren().removeAll();

        score = 0;
        if (modeSel.getValue().equals("Countries")) {
            selPrompt = countryPrompts.get(rand.nextInt(countryPrompts.size()));
        } else if (modeSel.getValue().equals("Cities")) {
            selPrompt = cityPrompts.get(rand.nextInt(cityPrompts.size()));
        }

        promptLbl.setText(selPrompt.pTitleText);
        failedLbl.setText(null);
        System.out.println("MODE = " + modeSel.getValue() + "\n TITLE = " + selPrompt.pTitleText);
        time = startTime;
        timeline.play();
        seeMissedAndGotToggleBtn.setVisible(false);
        active = true;
    }

    private void insertAns() {
        System.out.println("correct");
        time = 30;
        gotList.add(inputField.getText().toLowerCase());
        scoreLbl.setText(String.valueOf(score += 1));
        Label insert = new Label(inputField.getText().toUpperCase());
        insert.getStyleClass().add("generalBox2");
        viewPane.getChildren().add(insert);
    }

    private void submitAns() {

        // IDEA for LATER-
        // for faster results, make it so while the user is typing, it creates on
        // obsList of cities that match what is so far
        // typed.

        if (!gotList.contains(inputField.getText().toLowerCase())) {

            // countries
            if (modeSel.getValue().equals("Countries")) {
                switch (selPrompt.type) {
                    case "continent":
                        try {
                            String submitted = (home.getCorTbyName(inputField.getText()).cont);
                        } catch (Exception e) {
                            System.out.println("failed");
                        }
                        if ((home.getCorTbyName(inputField.getText()).cont).equals(selPrompt.lookFor)) {
                            insertAns();
                        } else {
                            negativeResponse(2, "Not in " + selPrompt.lookFor);
                        }
                }
                // cities
            } else if (modeSel.getValue().equals("Cities")) {
                switch (selPrompt.type) {
                    case "country":
                        try {
                            String submit = (home.getCorTCityByName(inputField.getText(), "US").name);
                        } catch (Exception e) {
                            System.out.println("failed");
                        }
                        if ((home.getCorTCityByName(inputField.getText(), "US")).country.equals(selPrompt.lookFor)) {
                            insertAns();
                        } else {
                            negativeResponse(2, "Not in " + home.getCorTbyIndex(selPrompt.lookFor).name);
                        }
                        break;

                    case "continent":
                        List<home.CorT> continentCorTs = home.returnCorTsortedByContList(selPrompt.lookFor);
                        for (home.CorT cort : continentCorTs) {
                            home.City foundOrNot = home.getCorTCityByName(inputField.getText(), cort.index);
                            if (foundOrNot != null) {
                                System.out.println("found!");
                            } else {
                                negativeResponse(2, "Not in " + selPrompt.lookFor);
                            }

                        }

                }
            }

        } else {
            negativeResponse(3, null);
        }

    }

    private void negativeResponse(Integer type, String response) {
        switch (type) {
            case 0:
                failedLbl.setText("");
                break;
            case 1:
                active = false;
                seeMissedAndGotToggleBtn.setVisible(true);
                failedLbl.setText("Ran out of time.");
                for (Node i : changeWhenWrongAndRightNodes) {
                    i.getStyleClass().add("buttonStyle2-1");
                }
                break;
            case 2:
                active = false;
                seeMissedAndGotToggleBtn.setVisible(true);
                failedLbl.setText(response);
                submitBtn.getStyleClass().add("buttonStyle2-1");
                for (Node i : changeWhenWrongAndRightNodes) {
                    i.getStyleClass().add("buttonStyle2-1");
                }
                break;
            case 3:
                failedLbl.setText("Already done");
                break;
        }

        active = false;
    }

}