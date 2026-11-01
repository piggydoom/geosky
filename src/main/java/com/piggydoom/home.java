package com.piggydoom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static final File geoData = new File("src\\main\\resources\\com\\piggydoom\\geoData.json");

    public static class CorT {
        public Integer type;
        public String index;
        public String capital;
        public String area;
        public String pop;
        public String desc;

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

    ObservableList<CorT> totalCorTs = FXCollections.observableArrayList();
    ObservableList<CorT> t1CorTs = FXCollections.observableArrayList();
    ObservableList<CorT> t234CorTs = FXCollections.observableArrayList();

    public void initialize() {
        // pre-sort JSON objs by type.
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<CorT> CorTJSON = mapper.readValue(geoData, new TypeReference<List<CorT>>() {
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
    public static CorT getCorT(String index) {
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
