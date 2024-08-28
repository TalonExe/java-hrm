package com.talon.models;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Payroll {
    private static final String PAYROLL_JSON_PATH = "/data/payrollDetails.json";

    private final float epf_employee = 0.13f;
    
    public float getEmployeeEpf() {
        return this.epf_employee;
    }

    private final float epf_employer = 0.11f;
    
    public float getEmployerEpf() {
        return this.epf_employer;
    }

    private final float socso_employer = 0.18f;
    
    public float getEmployerSocso() {
        return this.socso_employer;
    }

    private final float socso_employee = 0.005f;
    
    public float getEmployeeSocso() {
        return this.socso_employee;
    }
    
    private float eis = 0.002f;

    public float getEmployeeEis() {
        return this.eis;
    }

    public float getEmployerEis() {
        return this.eis;
    }

    public void setEis(float eis) {
        this.eis = eis;
    }

    private float pcb = 0.05f;

    public float getEmployeePcb() {
        return this.pcb;
    }

    public void setPcb(float pcb) {
        this.pcb = pcb;
    }

    private float salary;

    public float getEmployeeSalary() {
        return this.salary;
    }

    public void setEmployeeSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("%f %f %f %f %f", getEmployeeEpf(), getEmployeeSocso(), getEmployeeEis(), getEmployeePcb(), getEmployeeSalary());
    }

    public static Payroll findByUsername(String username) throws Exception {
        Gson gson = new Gson();
        try (InputStream inputStream = Employee.class.getResourceAsStream(PAYROLL_JSON_PATH)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + PAYROLL_JSON_PATH);
            }
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                Type dataType = new TypeToken<Map<String, Payroll>>() {}.getType();
                Map<String, Payroll> payroll = gson.fromJson(reader, dataType);
                Payroll output = payroll.get(username);
                if (output == null) {
                    throw new Exception("Payroll with username " + username + " is not found");
                }
                System.out.println(output);
                return output;
            }
        }
    }
}