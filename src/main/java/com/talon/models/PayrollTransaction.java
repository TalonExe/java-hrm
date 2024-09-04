package com.talon.models;

import java.time.LocalDate;

public class PayrollTransaction {
    private String employeeId;
    private String transactionDate;
    private double grossSalary;
    
    // Employee contributions
    private double employeeEpf;
    private double employeeSocso;
    private double employeeEis;
    private double employeeTax;
    
    // Employer contributions
    private double employerEpf;
    private double employerSocso;
    private double employerEis;
    
    private double pcb;
    private double latePenalty;
    private double netSalary;

    public PayrollTransaction(String employeeId, String transactionDate, double grossSalary) {
        this.employeeId = employeeId;
        this.transactionDate = transactionDate;
        this.grossSalary = grossSalary;
        calculateContributions();
        calculateNetSalary();
    }

    private void calculateContributions() {
        // Employee contributions
        this.employeeEpf = grossSalary * 0.11;
        this.employeeSocso = grossSalary * 0.005;
        this.employeeEis = grossSalary * 0.002;
        this.employeeTax = grossSalary * 0.05;

        // Employer contributions
        this.employerEpf = grossSalary * 0.13;
        this.employerSocso = grossSalary * 0.018;
        this.employerEis = grossSalary * 0.002;

        // PCB calculation
        this.pcb = this.employeeTax / 12;
    }

    private void calculateNetSalary() {
        this.netSalary = grossSalary - employeeEpf - employeeSocso - employeeEis - pcb - latePenalty;
    }

    // Getters and setters for all fields

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
        calculateContributions();
        calculateNetSalary();
    }

    public double getEmployeeEpf() {
        return employeeEpf;
    }

    public double getEmployeeSocso() {
        return employeeSocso;
    }

    public double getEmployeeEis() {
        return employeeEis;
    }

    public double getEmployeeTax() {
        return employeeTax;
    }

    public double getEmployerEpf() {
        return employerEpf;
    }

    public double getEmployerSocso() {
        return employerSocso;
    }

    public double getEmployerEis() {
        return employerEis;
    }

    public double getPcb() {
        return pcb;
    }

    public double getLatePenalty() {
        return latePenalty;
    }

    public void setLatePenalty(double latePenalty) {
        this.latePenalty = latePenalty;
        calculateNetSalary();
    }

    public double getNetSalary() {
        return netSalary;
    }

    public LocalDate getTransactionDateAsLocalDate() {
        return LocalDate.parse(this.transactionDate);
    }

    public void setEmployeeEpf(double employeeEpf) {
        this.employeeEpf = employeeEpf;
    }

    public void setEmployeeSocso(double employeeSocso) {
        this.employeeSocso = employeeSocso;
    }

    public void setEmployeeEis(double employeeEis) {
        this.employeeEis = employeeEis;
    }

    public void setEmployeeTax(double employeeTax) {
        this.employeeTax = employeeTax;
    }

    public void setEmployerEpf(double employerEpf) {
        this.employerEpf = employerEpf;
    }

    public void setEmployerSocso(double employerSocso) {
        this.employerSocso = employerSocso;
    }

    public void setEmployerEis(double employerEis) {
        this.employerEis = employerEis;
    }

    public void setPcb(double pcb) {
        this.pcb = pcb;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    
}
