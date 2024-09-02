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
            // Create a JsonArray to hold all employee entries
            JsonArray usersArray = new JsonArray();
            
            // Wrap each employee entry in a JsonObject with the UUID as the key
            for (Map.Entry<String, Employee> entry : employeeList.entrySet()) {
                JsonObject userObject = new JsonObject();
                userObject.add(entry.getKey(), gson.toJsonTree(entry.getValue()));
                usersArray.add(userObject);
            }
    
            // Create the final JsonObject to hold the Users array
            JsonObject finalObject = new JsonObject();
            finalObject.add("Users", usersArray);
    
            // Write the JSON object to the file
            gson.toJson(finalObject, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Employee findByUsername(String username) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            for (Employee employee : employees.values()) {
                if (employee.getUsername().equalsIgnoreCase(username)) {
                    return employee;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateLoginStatus(String username, String loginStatus) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            String employeeId = getEmployeeIdByUsername(employees, username);
            if (employeeId != null) {
                Employee employee = employees.get(employeeId);
                if (loginStatus.equalsIgnoreCase("SUCCESS")) {
                    employee.setLoginAttempts(0);
                    employee.setAccountStatus("ACTIVE");
                } else {
                    employee.setLoginAttempts(employee.getLoginAttempts() + 1);
                    if (employee.getLoginAttempts() >= 3) {
                        employee.setAccountStatus("LOCKED");
                    }
                }
                // Update the employee in the map
                employees.put(employeeId, employee);
                // Write the updated data back to the file
                WriteData(employees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }

    public static Employee createEmployee(String username, String password, String role) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            for (Employee employee : employees.values()) {
                if (employee.getUsername().equalsIgnoreCase(username)) {
                    throw new Exception("Username already exists");
                }
            }
            String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            String id = UUID.randomUUID().toString();
            Employee newEmployee = new Employee(username, hashedPassword, role);
            employees.put(id, newEmployee);
            WriteData(employees);
            return newEmployee;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updatePassword(String username, String newPassword) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            String employeeId = getEmployeeIdByUsername(employees, username);
            if (employeeId != null) {
                String hashedPassword = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray());
                employees.get(employeeId).setPassword(hashedPassword);
                WriteData(employees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateRole(String username, String newRole) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            String employeeId = getEmployeeIdByUsername(employees, username);
            if (employeeId != null) {
                employees.get(employeeId).setRole(newRole);
                WriteData(employees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unlockAccount(String username) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            String employeeId = getEmployeeIdByUsername(employees, username);
            if (employeeId != null) {
                employees.get(employeeId).setAccountStatus("ACTIVE");
                WriteData(employees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to get an employee's UUID by their username
    private static String getEmployeeIdByUsername(Map<String, Employee> employees, String username) {
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            if (entry.getValue().getUsername().equalsIgnoreCase(username)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
