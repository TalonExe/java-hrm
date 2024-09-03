package com.talon.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.talon.models.Leave;

public class LeaveUtils {
    private static final String LEAVE_FILE_PATH = "data/leaveDetails.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void addLeaveApplication(String employeeUuid, Leave leave) {
        JsonObject jsonObject = readLeaveData();
        JsonArray leavesArray = jsonObject.getAsJsonArray("Leaves");

        boolean employeeFound = false;
        for (int i = 0; i < leavesArray.size(); i++) {
            JsonObject employeeLeave = leavesArray.get(i).getAsJsonObject();
            if (employeeLeave.get("employeeUuid").getAsString().equals(employeeUuid)) {
                JsonArray leaves = employeeLeave.getAsJsonArray("leaves");
                leaves.add(gson.toJsonTree(leave));
                employeeFound = true;
                break;
            }
        }

        if (!employeeFound) {
            JsonObject newEmployeeLeave = new JsonObject();
            newEmployeeLeave.addProperty("employeeUuid", employeeUuid);
            JsonArray leaves = new JsonArray();
            leaves.add(gson.toJsonTree(leave));
            newEmployeeLeave.add("leaves", leaves);
            leavesArray.add(newEmployeeLeave);
        }

        writeLeaveData(jsonObject);
    }

    private static JsonObject readLeaveData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LEAVE_FILE_PATH))) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonObject();
        }
    }

    private static void writeLeaveData(JsonObject jsonObject) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEAVE_FILE_PATH))) {
            gson.toJson(jsonObject, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
