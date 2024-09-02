package com.talon.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.talon.models.Payroll;

public class PayrollUtils {
    private static final String FILE_PATH = "data/payrollDetails.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Method to read existing payroll data
    public static Map<String, Payroll> ReadPayrollData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray payrollArray = jsonObject.getAsJsonArray("Payroll");

            Map<String, Payroll> payrolls = new HashMap<>();
            for (JsonElement element : payrollArray) {
                JsonObject payrollObject = element.getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : payrollObject.entrySet()) {
                    String employeeId = entry.getKey();
                    JsonObject payrollDetails = entry.getValue().getAsJsonObject();
                    float grossSalary = payrollDetails.get("grossSalary").getAsFloat();
                    LocalDate createdDate = LocalDate.parse(payrollDetails.get("createdDate").getAsString());

                    Payroll payroll = new Payroll(employeeId, grossSalary);
                    payroll.setCreationDate(createdDate);
                    payrolls.put(employeeId, payroll);
                }
            }
            return payrolls;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    // Method to write updated payroll data
    public static void WritePayrollData(Map<String, Payroll> payrollList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            JsonArray payrollArray = new JsonArray();

            for (Map.Entry<String, Payroll> entry : payrollList.entrySet()) {
                JsonObject payrollObject = new JsonObject();
                JsonObject payrollDetails = new JsonObject();

                Payroll payroll = entry.getValue();
                payrollDetails.addProperty("grossSalary", payroll.getGrossSalary());
                payrollDetails.addProperty("netSalary", payroll.getNetSalary());  // Include netSalary if needed
                payrollDetails.addProperty("createdDate", payroll.getCreationDate().toString());
                payrollDetails.addProperty("epfEmployee", payroll.getEmployeeEpf() * payroll.getGrossSalary());
                payrollDetails.addProperty("epfEmployer", payroll.getEmployerEpf() * payroll.getGrossSalary());
                payrollDetails.addProperty("socsoEmployee", payroll.getEmployeeSocso() * payroll.getGrossSalary());
                payrollDetails.addProperty("socsoEmployer", payroll.getEmployerSocso() * payroll.getGrossSalary());
                payrollDetails.addProperty("eis", payroll.getEis() * payroll.getGrossSalary());
                payrollDetails.addProperty("pcb", payroll.getPcb() * payroll.getGrossSalary());

                payrollObject.add(entry.getKey(), payrollDetails);
                payrollArray.add(payrollObject);
            }

            JsonObject finalObject = new JsonObject();
            finalObject.add("Payroll", payrollArray);

            gson.toJson(finalObject, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to create a new payroll entry
    public static Payroll createPayroll(String username, float salary) throws Exception {
        try {
            // Read existing payroll data
            Map<String, Payroll> payrolls = ReadPayrollData();

            // Create a new Payroll object
            Payroll newPayroll = new Payroll(username, salary);
            newPayroll.setCreationDate(LocalDate.now());  // Set the current date

            // Update the payroll map with the new entry
            payrolls.put(username, newPayroll);

            // Save the updated payroll data back to the file
            WritePayrollData(payrolls);

            return newPayroll;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error creating payroll");
        }
    }

    // Method to find payroll by username
    public static Payroll findPayrollByID(String employeeId) {
        try {
            // Read existing payroll data
            Map<String, Payroll> payrolls = ReadPayrollData();
            System.out.println(payrolls.get(employeeId));
            System.out.println(
                "sdfsdfsf"
            );

            // Retrieve and return the payroll entry for the given username
            return payrolls.get(employeeId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
