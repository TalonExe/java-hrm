package com.talon.models;

import java.time.LocalDate;

public class Payroll {
    
    // EPF (Employee Provident Fund) contribution rates
    private static final float EPF_EMPLOYEE = 0.13f;
    private static final float EPF_EMPLOYER = 0.11f;

    // SOCSO (Social Security Organization) contribution rates
    private static final float SOCSO_EMPLOYER = 0.18f;
    private static final float SOCSO_EMPLOYEE = 0.005f;

    // EIS (Employment Insurance System) and PCB (Scheduled Tax Deduction)
    private float eis = 0.002f;
    private float pcb = 0.05f;

    // Salary
    private String name;
    private float grossSalary;  // Store the original gross salary
    private float netSalary;    // Store the calculated net salary
    private LocalDate creationDate;

    // Constructor
    public Payroll(String name, float grossSalary) {
        this.name = name;
        this.grossSalary = grossSalary;
        this.netSalary = calculateNetSalary(grossSalary); // Calculate net salary during instantiation
        this.creationDate = LocalDate.now();
    }

    public Payroll(float grossSalary, float eis, float pcb, LocalDate creationDate) {
        this.grossSalary = grossSalary;
        this.eis = eis;
        this.pcb = pcb;
        this.netSalary = calculateNetSalary(grossSalary); // Calculate net salary during instantiation
        this.creationDate = creationDate;
    }

    // Getters for EPF and SOCSO contributions
    public float getEmployeeEpf() {
        return EPF_EMPLOYEE;
    }

    public float getEmployerEpf() {
        return EPF_EMPLOYER;
    }

    public float getEmployerSocso() {
        return SOCSO_EMPLOYER;
    }

    public float getEmployeeSocso() {
        return SOCSO_EMPLOYEE;
    }

    // Getters and setters for EIS and PCB
    public float getEis() {
        return this.eis;
    }

    public void setEis(float eis) {
        this.eis = eis;
        this.netSalary = calculateNetSalary(this.grossSalary); // Recalculate net salary if rates change
    }

    public float getPcb() {
        return this.pcb;
    }

    public void setPcb(float pcb) {
        this.pcb = pcb;
        this.netSalary = calculateNetSalary(this.grossSalary); // Recalculate net salary if rates change
    }

    // Getter and setter for salary
    public float getGrossSalary() {
        return this.grossSalary;
    }

    public String getName() {
        return name;
    }

    public float getNetSalary() {
        return this.netSalary;
    }

    public void setGrossSalary(float grossSalary) {
        this.grossSalary = grossSalary;
        this.netSalary = calculateNetSalary(grossSalary); // Recalculate net salary when gross salary changes
    }

    public void setName(String name) {
        this.name = name;
    }

    // Calculate total deductions
    private float calculateTotalDeductions(float grossSalary) {
        return (EPF_EMPLOYEE * grossSalary) + (SOCSO_EMPLOYEE * grossSalary) + (eis * grossSalary) + (pcb * grossSalary);
    }

    // Calculate net salary after deductions
    private float calculateNetSalary(float grossSalary) {
        return grossSalary - calculateTotalDeductions(grossSalary);
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return String.format(
            "Gross Salary: %.2f, Net Salary: %.2f, EPF (Employee): %.2f, SOCSO (Employee): %.2f, EIS: %.2f, PCB: %.2f",
            grossSalary, 
            netSalary, 
            getEmployeeEpf() * grossSalary, 
            getEmployeeSocso() * grossSalary, 
            getEis() * grossSalary, 
            getPcb() * grossSalary
        );
    }
}
