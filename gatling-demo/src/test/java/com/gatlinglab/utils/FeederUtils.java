package com.gatlinglab.utils;

import io.gatling.javaapi.core.FeederBuilder;

import java.util.*;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;

public class FeederUtils {

    public static FeederBuilder<Object> buildCircularFeederId() {
        List<Map<String, Object>> idList = new ArrayList<>();
        idList.add(Map.of("id", 1));
        idList.add(Map.of("id", 2));
        idList.add(Map.of("id", 3));
        idList.add(Map.of("id", 4));
        idList.add(Map.of("id", 5));
        return listFeeder(idList).circular();
    }

    public static Iterator<Map<String, Object>> buildRandomFeederId(int max) {
        Random random = new Random();
        return Stream.generate(() -> {
            Map<String, Object> data = new HashMap<>();
            data.put("id", random.nextInt(max) + 1); // 1 à 50 000
            return data;
        }).iterator();
    }

    public static FeederBuilder.Batchable<String> buildCsvFeederCategory() {
        return  csv("tp4/categories.csv").random();
    }

    public static FeederBuilder.FileBased<Object> buildJsonFeederPrefixesSize() {
        return  jsonFile("tp4/prefixes_size.json").random();
    }

    public static FeederBuilder.FileBased<Object> buildJsonFeederPrefixes() {
        return  jsonFile("tp4/prefixes.json").random();
    }

    // Feeder random pour la size
    // Très similaire au Feeder pour ID
    public static Iterator<Map<String, Object>> buildRandomFeederSize(int max) {
        Random random = new Random();
        return Stream.generate(() -> {
            Map<String, Object> data = new HashMap<>();
            data.put("size", random.nextInt(max) + 1); // 1 à 50 000
            return data;
        }).iterator();
    }

    public static Iterator<Map<String, Object>> buildRandomFeederProperty(String property, int max) {
        Random random = new Random();
        return Stream.generate(() -> {
            Map<String, Object> data = new HashMap<>();
            data.put(property, random.nextInt(max) + 1); // 1 à 50 000
            return data;
        }).iterator();
    }
}
