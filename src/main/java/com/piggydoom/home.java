package com.piggydoom;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class home {

    @FXML
    public Button hbFlashcards;

    @FXML
    public void toFlashcards() throws IOException {
        App.setRoot("flashcards");
    }

    File geoData = new File("src\\main\\resources\\com\\piggydoom\\geoData.json");

    public static class FlagCountry {
        public String capital;
        public String area;
        public String pop;
        public String index; // 2. Changed to String to match "fr" in your JSON

        // 3. Added empty constructor for Jackson
        public FlagCountry() {}

        public FlagCountry(String capital, String area, String pop, String index) {
            this.capital = capital;
            this.area = area;
            this.pop = pop;
            this.index = index;
        }
    }

    public Image adF = new Image(getClass().getResource("/com/piggydoom/flags/ad.png").toExternalForm());

    // to consider; do i import all at once or on the spot?

    // the plan;
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

    public void initialize() {
        System.out.println("france JSON");

        ObjectMapper objectMapper = new ObjectMapper();

        
        //how do you read an object from a JSON file without reading the entire JSON file, like just grab the one
        try {
            FlagCountry france = objectMapper.readValue(geoData, FlagCountry.class);
            System.out.println("Capital: " + france.capital);
            System.out.println("Area: " + france.area);
            System.out.println("Population: " + france.pop);
            System.out.println("Index: " + france.index);
        } catch (IOException e) {
            throw new RuntimeException("Could not read country data", e);
        }

    }

    public void getRandomCT(boolean Countries, boolean territories){

    }

}

