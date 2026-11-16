package com.piggydoom;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
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

        public CorT(String index, Integer type, String name, String cont, String capital, String area, String pop,
                String desc) {

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

        public City(String id, String name, String country, String admin1, String admin2, String lat, String lon,
                String pop) {

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

    public static List<CorT> returnCorTsortedByContList(String selCont) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<CorT> totalList = objectMapper.readValue(countryData, new TypeReference<List<CorT>>() {
            });
            return totalList.stream()
                    .filter(user -> user.cont.equals(selCont))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("it was line 120 again", e);
        }

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

    public static City getCorTCityByName(String name, String CorTIndex) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<City> totalList = objectMapper.readValue(
                    new File("src\\main\\resources\\com\\piggydoom\\JSON\\cities\\" + CorTIndex + ".json"),
                    new TypeReference<List<City>>() {
                    });

            Optional<City> selIndex = totalList.stream()
                    .filter(sel -> sel.name.equalsIgnoreCase(name))
                    .findFirst();
            return selIndex.orElse(null);
        } catch (IOException e) {
            throw new RuntimeException("Could not read country data", e);
        }
    }

    public static List<City> createCitySampleSpace(String lookIn, String lookFor) {
        File[] allCityFiles = new File("src\\main\\resources\\com\\piggydoom\\JSON\\cities").listFiles();
        System.out.println("allCityFiles" + allCityFiles.length);
        List<City> finalList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
                   for (File i : allCityFiles) {
                try {
                    List<City> theseCities = objectMapper.readValue(
                            i,
                            new TypeReference<List<City>>() {
                            });
                    Field field = City.class.getDeclaredField(lookIn);

                    List<City> matchingCities = theseCities.stream()
                            .filter(x -> {
                                try {
                                    Object value = field.get(x);
                                    return value != null && value.toString().equalsIgnoreCase(lookFor);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .collect(Collectors.toList());
                    finalList.addAll(matchingCities);
                } catch (IOException | NoSuchFieldException e) {
                    throw new RuntimeException("Could not read country data", e);
                }
            }
        
        return finalList;
    }

    // public static CorT getRandCorT(Integer type){
    // ThreadLocalRandom.current().nextInt(0, home.totalCorTs.size() + 1);
    // }
    public static Image CorTReturnImage(String index) {
        try {
            return new Image(home.class.getResource("/com/piggydoom/media/flags/" + index + ".png").toExternalForm());
        } catch (Exception e) {
            return new Image(home.class.getResource("/com/piggydoom/media/missing/missingFlag.png").toExternalForm());
        }
    }

    // public static String CorTExchangeNameForIndex(String name){

    // }

}
