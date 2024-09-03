package com.talon.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.talon.models.Employee;
import com.talon.models.Payroll;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class EmployeeUtils {
    private static final String FILE_PATH = "data/employeeList.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Map<String, Employee> ReadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray usersArray = jsonObject.getAsJsonArray("Users");
            Map<String, Employee> employees = new HashMap<>();
            for (JsonElement element : usersArray) {
                JsonObject userObject = element.getAsJsonObject();
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
            JsonArray usersArray = new JsonArray();
            for (Map.Entry<String, Employee> entry : employeeList.entrySet()) {
                JsonObject userObject = new JsonObject();
                userObject.add(entry.getKey(), gson.toJsonTree(entry.getValue()));
                usersArray.add(userObject);
            }
            JsonObject finalObject = new JsonObject();
            finalObject.add("Users", usersArray);
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
                employees.put(employeeId, employee);
                WriteData(employees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }

    public static Employee createEmployee(String username, String password, String name, String gender, 
                                        String passport, String identificationCard, String phoneNumber, 
                                        String birthDate, String email, String address, String emergencyContact, 
                                        String role, String position) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            for (Employee employee : employees.values()) {
                if (employee.getUsername().equalsIgnoreCase(username)) {
                    throw new Exception("Username already exists");
                }
            }
            String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            String id = UUID.randomUUID().toString();
            Employee newEmployee = new Employee(username, hashedPassword, name, gender, passport, 
                                            identificationCard, phoneNumber, birthDate, email, 
                                            address, emergencyContact, role, position, 0, "ACTIVE", null);
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

    public static String getEmployeeIdByUsername(Map<String, Employee> employees, String username) {
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            if (entry.getValue().getUsername().equalsIgnoreCase(username)) {
                return entry.getKey();
            }
        }
        return null;
    }

    // New methods for managing payroll records
    public static void addPayrollRecord(String username, Payroll payrollRecord) throws Exception {
        Map<String, Employee> employees = ReadData();
        String employeeId = getEmployeeIdByUsername(employees, username);
        if (employeeId != null) {
            Employee employee = employees.get(employeeId);
            employee.addPayrollRecord(payrollRecord);
            WriteData(employees);
        }
    }

    public static List<Payroll> getPayrollRecords(String username) throws Exception {
        Map<String, Employee> employees = ReadData();
        String employeeId = getEmployeeIdByUsername(employees, username);
        if (employeeId != null) {
            return employees.get(employeeId).getPayrollRecords();
        }
        return null;
    }

    public static Payroll getLatestPayrollRecord(String username) throws Exception {
        Map<String, Employee> employees = ReadData();
        String employeeId = getEmployeeIdByUsername(employees, username);
        if (employeeId != null) {
            return employees.get(employeeId).getLatestPayrollRecord();
        }
        return null;
    }

    public static Payroll getPayrollRecordByDate(String username, String date) throws Exception {
        Map<String, Employee> employees = ReadData();
        String employeeId = getEmployeeIdByUsername(employees, username);
        if (employeeId != null) {
            return employees.get(employeeId).getPayrollRecordByDate(date);
        }
        return null;
    }

    public static Map<String, List<Payroll>> getAllPayrollRecords() throws Exception {
        Map<String, Employee> employees = ReadData();
        Map<String, List<Payroll>> allPayrollRecords = new HashMap<>();

        for (Employee employee : employees.values()) {
            allPayrollRecords.put(employee.getUsername(), employee.getPayrollRecords());
        }

        return allPayrollRecords;
    }
}
