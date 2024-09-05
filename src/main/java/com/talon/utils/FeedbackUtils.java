package com.talon.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.talon.models.Feedback;

public class FeedbackUtils {
    private static final String FILE_PATH = "data/feedbackList.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Map<String, Feedback> ReadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            return gson.fromJson(reader, new TypeToken<Map<String, Feedback>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void WriteData(Map<String, Feedback> feedbackList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            gson.toJson(feedbackList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
