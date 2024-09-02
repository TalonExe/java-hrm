package com.talon.models;

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
    private float salary;

    // Constructor
    public Payroll(String name, float salary) {
        this.name = name;
        this.salary = salary;
    }

    public Payroll(float salary, float eis, float pcb) {
        this.salary = salary;
        this.eis = eis;
        this.pcb = pcb;
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
    }

    public float getPcb() {
        return this.pcb;
    }

    public void setPcb(float pcb) {
        this.pcb = pcb;
    }

    // Getter and setter for salary
    public float getSalary() {
        return this.salary;
    }

    public String getName() {
        return name;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Calculate total deductions
    public float calculateTotalDeductions() {
        return (EPF_EMPLOYEE * salary) + (SOCSO_EMPLOYEE * salary) + (eis * salary) + (pcb * salary);
    }

    // Calculate net salary after deductions
    public float calculateNetSalary() {
        return salary - calculateTotalDeductions();
    }

    @Override
    public String toString() {
        return String.format(
            "Salary: %.2f, EPF (Employee): %.2f, SOCSO (Employee): %.2f, EIS: %.2f, PCB: %.2f, Net Salary: %.2f",
            salary, 
            getEmployeeEpf() * salary, 
            getEmployeeSocso() * salary, 
            getEis() * salary, 
            getPcb() * salary, 
            calculateNetSalary()
        );
    }
}
