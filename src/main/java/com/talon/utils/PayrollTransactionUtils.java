package com.talon.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import com.talon.models.PayrollTransaction;
import java.lang.reflect.Type;
import java.time.LocalDate; // Assuming this import is needed for LocalDateAdapter

public class PayrollTransactionUtils {
    private static final String FILE_PATH = "data/payrollTransaction.json";
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Assuming this import is needed for LocalDateAdapter
        .setPrettyPrinting()
        .create();

    // Read data from the JSON file
    public static Map<String, PayrollTransaction> ReadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            Type type = new TypeToken<Map<String, PayrollTransaction>>() {}.getType();
            Map<String, PayrollTransaction> data = gson.fromJson(reader, type);
            return data != null ? data : new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    // Write data to the JSON file
    public static void WriteData(Map<String, PayrollTransaction> data) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create a new payroll transaction
    public static void CreatePayrollTransaction(PayrollTransaction transaction) {
        String id = UUID.randomUUID().toString();
        Map<String, PayrollTransaction> data = ReadData();
        data.put(id, transaction);
        WriteData(data);
    }

    // Get a specific payroll transaction by ID
    public static PayrollTransaction GetPayrollTransaction(String id) {
        Map<String, PayrollTransaction> data = ReadData();
        return data.get(id);
    }

    // Update an existing payroll transaction
    public static void UpdatePayrollTransaction(String id, PayrollTransaction transaction) {
        Map<String, PayrollTransaction> data = ReadData();
        if (data.containsKey(id)) {
            data.put(id, transaction);
            WriteData(data);
        } else {
            throw new IllegalArgumentException("Payroll transaction with ID " + id + " not found.");
        }
    }

    // Delete a payroll transaction
    public static void DeletePayrollTransaction(String id) {
        Map<String, PayrollTransaction> data = ReadData();
        if (data.containsKey(id)) {
            data.remove(id);
            WriteData(data);
        } else {
            throw new IllegalArgumentException("Payroll transaction with ID " + id + " not found.");
        }
    }
}
