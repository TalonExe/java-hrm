package com.talon.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.talon.models.Employee;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class EmployeeUtils {
    private static final String FILE_PATH = "data/employeeList.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // public static Map<String, Employee> ReadData() {
    //     try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
    //         return gson.fromJson(reader, new TypeToken<Map<String, Employee>>() {}.getType());
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         return new HashMap<>();
    //     }
    // }

    public static Map<String, Employee> ReadData() {
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
        // Parse the outer JSON object
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

        // Extract the "Users" array
        JsonArray usersArray = jsonObject.getAsJsonArray("Users");

        // Initialize the map to hold the UUIDs and Employee objects
        Map<String, Employee> employees = new HashMap<>();

        // Loop through each element in the "Users" array
        for (JsonElement element : usersArray) {
            // Each element in the array is a JSON object with one key-value pair
            JsonObject userObject = element.getAsJsonObject();

            // Extract the UUID (key) and the Employee object (value)
            for (Map.Entry<String, JsonElement> entry : userObject.entrySet()) {
                String uuid = entry.getKey();
                Employee employee = gson.fromJson(entry.getValue(), Employee.class);
                employees.put(uuid, employee);
            }
        }

        return employees;

        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    

    public static void WriteData(Map<String, Employee> employeeList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            gson.toJson(employeeList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Employee findByUsername(String username) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            Employee output = null;
            for (Employee employee : employees.values()) {
                if (employee.getUsername().equalsIgnoreCase(username)) {
                    output = employee;
                    break;
                }
            }
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateLoginStatus(String username, String loginStatus) throws Exception {
        try {
            Employee employee = findByUsername(username);
            Map<String, Employee> employees = ReadData();
            if (loginStatus.equalsIgnoreCase("SUCCESS")) {
                employee.setLoginAttempts(0);
                employee.setAccountStatus("ACTIVE");
            } else {
                employee.setLoginAttempts(employee.getLoginAttempts() + 1);
                if (employee.getLoginAttempts() >= 3) {
                    employee.setAccountStatus("LOCKED");
                }
            }
            // Find employee id
            String employeeId = null;
            for (String key : employees.keySet()) {
                if (employees.get(key).getUsername().equalsIgnoreCase(username)) {
                    employeeId = key;
                    break;
                }
            }
            employees.put(employeeId, employee);
            WriteData(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }

    public static Employee createEmployee(String username, String password, String role) throws Exception {
        try {
            // Check if username already exists
            Map<String, Employee> employees = ReadData();
            for (Employee employee : employees.values()) {
                if (employee.getUsername().equalsIgnoreCase(username)) {
                    throw new Exception("Username already exists");
                }
            }
            String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            String id = UUID.randomUUID().toString();
            employees.put(id, new Employee(username, hashedPassword, role));
            WriteData(employees);
            return employees.get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updatePassword(String username, String newPassword) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            String hashedPassword = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray());
            employees.get(username).setPassword(hashedPassword);
            WriteData(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateRole(String username, String newRole) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            employees.get(username).setRole(newRole);
            WriteData(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unlockAccount(String username) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            employees.get(username).setAccountStatus("ACTIVE");
            WriteData(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}