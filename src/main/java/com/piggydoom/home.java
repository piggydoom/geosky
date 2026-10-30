package com.piggydoom;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
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

    public static class CorT {
        public Integer type;
        public String index;
        public String capital;
        public String area;
        public String pop;
        public String desc;

        //"type"
        //1- country recognised by the U.N
        //2- territory
        //3- disputed
        //4- other

        public CorT() {
        }

        public CorT(String index, Integer type, String capital, String area, String pop, String desc) {
            this.index = index;
            this.type = type;
            this.capital = capital;
            this.area = area;
            this.pop = pop;
            this.desc = desc;
        }
    }

    public Image adF = new Image(getClass().getResource("/com/piggydoom/flags/ad.png").toExternalForm());

    public void initialize() {
    }

    @FXML
    public void testCountry() {
        CorT fr = getCorT("fr");
        // System.out.println(getCorT("fr").index);
        System.out.println(fr.capital);
        System.out.println(fr.pop);
    }

    public CorT getCorT(String index) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<CorT> totalList = objectMapper.readValue(geoData, new TypeReference<List<CorT>>() {
            });

            Optional<CorT> selIndex = totalList.stream()
                    .filter(user -> user.index.equals(index))
                    .findFirst();
            return selIndex.orElse(null);
        } catch (IOException e) {
            throw new RuntimeException("Could not read country data", e);
        }
    }

}
