package com.talon.models;

public class InternalWorkExperience extends BaseWorkExperience {
    private String department;
    private String role;
    private Integer grossSalary;

    public InternalWorkExperience(String position, String startDate, String endDate, String department, Integer grossSalary, String role) {
        super("Talon", position, startDate, endDate);
        this.department = department;
        this.grossSalary = grossSalary;
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(Integer grossSalary) {
        this.grossSalary = grossSalary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}