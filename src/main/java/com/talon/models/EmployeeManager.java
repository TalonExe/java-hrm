package com.talon.models;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EmployeeManager {
    private Map<String, Employee> employeeMap = new HashMap<>();

    public EmployeeManager() throws IOException {
        loadEmployees();
    }

    public Map<String, Employee> getEmployeeMap() {
        return employeeMap;
    }

    private void loadEmployees() throws IOException {
        // Load employees from JSON
        Gson gson = new Gson();
        try (var inputStream = Employee.class.getResourceAsStream("/data/employeeList.json")) {
            if (inputStream == null) {
                throw new IOException("File not found: /data/employeeList.json");
            }
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                Type userType = new TypeToken<Map<String, Employee>>() {}.getType();
                employeeMap = gson.fromJson(reader, userType);
            }
        }
    }

    public Employee findByUsername(String username) throws Exception {
        Employee employee = employeeMap.get(username);
        if (employee == null) {
            throw new Exception("User with username " + username + " is not found");
        }
        employee.payroll = Payroll.findByUsername(username);
        return employee;
    }

    public void addEmployee(Employee employee) {
        employeeMap.put(employee.getUsername(), employee);
        // Optionally, save to file or database
    }

    public void removeEmployee(String username) {
        employeeMap.remove(username);
        // Optionally, update file or database
    }
}
