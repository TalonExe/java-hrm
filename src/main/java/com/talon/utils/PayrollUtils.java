package com.talon.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PayrollUtils {
    private static final String FILE_PATH = "data/payrollDetails.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Map<String, Float> ReadPayrollData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray payrollArray = jsonObject.getAsJsonArray("Payroll");

            Map<String, Float> payrolls = new HashMap<>();
            for (JsonElement element : payrollArray) {
                JsonObject payrollObject = element.getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : payrollObject.entrySet()) {
                    String employeeId = entry.getKey();
                    float salary = entry.getValue().getAsJsonObject().get("salary").getAsFloat();
                    payrolls.put(employeeId, salary);
                }
            }
            return payrolls;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
