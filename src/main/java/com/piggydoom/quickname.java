package com.piggydoom;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piggydoom.home.City;

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

   

    // List<home.Prompt> cityPrompts = new ArrayList<>();
    // List<home.Prompt> countryPrompts = new ArrayList<>();
    List<Node> changeWhenWrongAndRightNodes = new ArrayList();
    int startTime = 30;
    int score;
    int time;
    int viewPaneMaxCapacityNum = 10;
    boolean active = false;
    int mode = 1;
    int endViewMode = 1; // 1 = what was gotten, 2 = what was missed
    home.Prompt selPrompt;
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

        modeSel.setOnAction(event -> {
            viewPane.getChildren().clear();
            active = false;
            time = 0;
            promptLbl.setText(null);
            inputField.setText(null);
            timeLbl.setText(null);
            scoreLbl.setText(null);
            timeline.stop();
            gotList.clear();
        });

        seeMissedAndGotToggleBtn.setOnAction(event -> {
            if (endViewMode == 1) {
                endViewMode = 2;
                seeMissedAndGotToggleBtn.getStyleClass().add("buttonStyle2-1");
                seeMissedAndGotToggleBtn.setText("See gotten");
                writeMissed(selPrompt);
            } else {
                endViewMode = 1;
                seeMissedAndGotToggleBtn.getStyleClass().clear();
                seeMissedAndGotToggleBtn.getStyleClass().add("buttonStyle2");
                seeMissedAndGotToggleBtn.setText("See missed");
                viewPane.getChildren().clear();
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
            selPrompt = home.listPromptsCorT.get(rand.nextInt(home.listPromptsCorT.size()));
        } else if (modeSel.getValue().equals("Cities")) {
            selPrompt = home.listPromptsCity.get(rand.nextInt(home.listPromptsCity.size()));
        }

        promptLbl.setText(selPrompt.pText);
        failedLbl.setText(null);
        System.out.println("MODE = " + modeSel.getValue() + "\n TITLE = " + selPrompt.pText);
        time = startTime;
        timeline.play();
        seeMissedAndGotToggleBtn.setVisible(false);
        active = true;

        submitBtn.getStyleClass().add("buttonStyle2");
                for (Node i : changeWhenWrongAndRightNodes) {
                    i.getStyleClass().add("buttonStyle2");
                }
    }

    private void insertAns() {
        System.out.println("correct");
        time = 30;
        timeLbl.setText(String.valueOf(time));
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
            if (selPrompt.mode == 1) {
                switch (selPrompt.type) {
                    case "continent":
                        try {
                            String submitted = (home.getCorTbyName(inputField.getText()).cont);
                        } catch (Exception e) {
                            try {
                                String submitted = (home.getCorTbyName(inputField.getText()).cont);
                            } catch (Exception e2) {

                                System.out.println("failed");
                            }
                        }
                        if ((home.getCorTbyName(inputField.getText()).cont).equals(selPrompt.lookFor)) {
                            insertAns();
                        } else {
                            negativeResponse(2, "Not in " + selPrompt.lookFor);
                        }
                }
                // cities
            } else if (selPrompt.mode == 0) {
                switch (selPrompt.type) {
                    case "country":
                        try {
                            String submit = (home.getCorTCityByName(inputField.getText(), selPrompt.lookFor).name);
                            // if this fails, try again but for all
                        } catch (Exception e) {
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

    private void writeMissed(home.Prompt prompt) {
        System.out.println("writeMissed triggered. mode " + prompt.mode + " | type " + prompt.type);
        viewPane.getChildren().clear();
        if (prompt.mode == 0) {
            switch (prompt.type) {
                case "country":
                    List<home.City> totalAvailList = home.createCitySampleSpace("country", prompt.lookFor);
                    System.out.println("total cities in " + prompt.lookFor + " = " + totalAvailList.size());
                    viewPane.getChildren().clear();
                    for (home.City i : totalAvailList) {
                        if (!gotList.contains(i) && viewPane.getChildren().size() < viewPaneMaxCapacityNum) {
                            Label missed = new Label(i.name.toUpperCase());
                            missed.getStyleClass().add("generalBox2");
                            viewPane.getChildren().add(missed);
                        }
                    }
                    break;
            }
        } else if (prompt.mode == 1) {
            System.out.println("CANNOT writeMissed() ||| MODE != 0 ||| SWAP TO CITIES MODE FOR PLAYTEST");
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