package com.piggydoom;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piggydoom.home.CorT;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    @FXML
    public void toQuickname() throws IOException {
        App.setRoot("quickname");
    }

    public static final File countryData = new File("src\\main\\resources\\com\\piggydoom\\JSON\\countryData.json");
     public static final File cities500 = new File("src\\main\\resources\\com\\piggydoom\\JSON\\cities500.json");

    public static class CorT {
        public String index;
        public Integer type;
        public String name;
        public String cont;
        public String capital;
        public String area;
        public String pop;
        public String desc;

        public CorT() {
        }

        public CorT(String index, Integer type, String name, String cont, String capital, String area, String pop, String desc) {

            this.index = index;
            this.type = type;
            this.name = name;
            this.cont = cont;
            this.capital = capital;
            this.area = area;
            this.pop = pop;
            this.desc = desc;
        }
    }

      public static class City {
        public String id;
        public String name;
        public String country;
        public String admin1;
        public String admin2;
        public String lat;
        public String lon;
        public String pop;

        public City() {
        }

        public City(String id, String name, String country, String admin1, String admin2, String lat, String lon, String pop) {

            this.id = id;
            this.name = name;
            this.country = country;
            this.admin1 = admin1;
            this.admin2 = admin2;
            this.lat = lat;
            this.lon = lon;
            this.pop = pop;
        }
    }

    

    public static ObservableList<CorT> totalCorTs = FXCollections.observableArrayList();
    public static ObservableList<CorT> t1CorTs = FXCollections.observableArrayList();
    public static ObservableList<CorT> t234CorTs = FXCollections.observableArrayList();

    public void initialize() {
        // pre-sort JSON objs by type.
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<CorT> CorTJSON = mapper.readValue(countryData, new TypeReference<List<CorT>>() {
            });
            totalCorTs.addAll(CorTJSON);

            for (CorT cort : totalCorTs) {
                if (cort.type == 1) {
                    t1CorTs.add(cort);
                } else {
                    t234CorTs.add(cort);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void testCountry() {
        // CorT fr = getCorT("fr");
        // // System.out.println(getCorT("fr").index);
        // System.out.println(fr.capital);
        // System.out.println(fr.pop);

        System.out.println(t234CorTs.get(0).capital);
    }

    // gets a specific
    public static CorT getCorTbyIndex(String index) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<CorT> totalList = objectMapper.readValue(countryData, new TypeReference<List<CorT>>() {
            });

            Optional<CorT> selIndex = totalList.stream()
                    .filter(user -> user.index.equals(index))
                    .findFirst();
            return selIndex.orElse(null);
        } catch (IOException e) {
            throw new RuntimeException("Could not read country data", e);
        }
    }
    public static CorT getCorTbyName(String name) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<CorT> totalList = objectMapper.readValue(countryData, new TypeReference<List<CorT>>() {
            });

            Optional<CorT> selIndex = totalList.stream()
                    .filter(sel -> sel.name.toLowerCase().equals(name.toLowerCase()))
                    .findFirst();
            return selIndex.orElse(null);
        } catch (IOException e) {
            throw new RuntimeException("Could not read country data", e);
        }
    }

    // public static CorT getRandCorT(Integer type){
    // ThreadLocalRandom.current().nextInt(0, home.totalCorTs.size() + 1);
    // }
    public static Image returnCorTImg(String index) {
        try {
            return new Image(home.class.getResource("/com/piggydoom/media/flags/" + index + ".png").toExternalForm());
        } catch (Exception e) {
            return new Image(home.class.getResource("/com/piggydoom/media/missing/missingFlag.png").toExternalForm());
        }
    }
}
