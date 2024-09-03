package com.talon.models;

import java.text.NumberFormat;
import java.time.LocalDate;

public class Payroll {

    // Static final fields for contribution rates
    private static final float EPF_EMPLOYEE_RATE = 0.13f;
    private static final float EPF_EMPLOYER_RATE = 0.11f;
    private static final float SOCSO_EMPLOYEE_RATE = 0.005f;
    private static final float SOCSO_EMPLOYER_RATE = 0.18f;
    private static final float EIS_RATE = 0.002f;
    private static final float PCB_RATE = 0.05f;

    private float grossSalary;
    private float netSalary;
    private String createdDate;
    private float epfEmployee;
    private float epfEmployer;
    private float socsoEmployee;
    private float socsoEmployer;
    private float eis;
    private float pcb;
    private String username;

    // Constructor to initialize the payroll with gross salary and creation date
    public Payroll(float grossSalary, LocalDate createdDate) {
        this.grossSalary = grossSalary;
        this.createdDate = createdDate.toString();

        // Calculate contributions based on static rates
        recalculateValues();
    }

    // Calculate total deductions (EPF, SOCSO, EIS, PCB)
    private float calculateTotalDeductions() {
        return epfEmployee + socsoEmployee + eis + pcb;
    }

    // Calculate net salary after deductions
    private float calculateNetSalary() {
        return grossSalary - calculateTotalDeductions();
    }

    // Set new gross salary and recalculate deductions and net salary
    public void setGrossSalary(float grossSalary) {
        this.grossSalary = grossSalary;
        recalculateValues(); // Recalculate all dependent values
    }

    // Getters and setters
    public float getGrossSalary() {
        return grossSalary;
    }

    public float getNetSalary() {
        return netSalary;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public float getEpfEmployee() {
        return epfEmployee;
    }

    public float getEpfEmployer() {
        return epfEmployer;
    }

    public float getSocsoEmployee() {
        return socsoEmployee;
    }

    public float getSocsoEmployer() {
        return socsoEmployer;
    }

    public float getEis() {
        return eis;
    }

    public float getPcb() {
        return pcb;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Recalculate deductions and net salary after updating gross salary
    private void recalculateValues() {
        this.epfEmployee = EPF_EMPLOYEE_RATE * grossSalary;
        this.epfEmployer = EPF_EMPLOYER_RATE * grossSalary;
        this.socsoEmployee = SOCSO_EMPLOYEE_RATE * grossSalary;
        this.socsoEmployer = SOCSO_EMPLOYER_RATE * grossSalary;
        this.eis = EIS_RATE * grossSalary;
        this.pcb = PCB_RATE * grossSalary;
        this.netSalary = calculateNetSalary();
    }

    @Override
    public String toString() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return String.format(
            "Gross Salary: %s, Net Salary: %s, Created Date: %s, EPF (Employee): %s, EPF (Employer): %s, SOCSO (Employee): %s, SOCSO (Employer): %s, EIS: %s, PCB: %s",
            currencyFormat.format(grossSalary), currencyFormat.format(netSalary), createdDate,
            currencyFormat.format(epfEmployee), currencyFormat.format(epfEmployer),
            currencyFormat.format(socsoEmployee), currencyFormat.format(socsoEmployer),
            currencyFormat.format(eis), currencyFormat.format(pcb)
        );
    }
}
