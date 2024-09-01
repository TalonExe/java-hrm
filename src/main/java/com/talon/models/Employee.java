package com.talon.models;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Employee {

    public enum Roles {
        SYSTEM_ADMIN,
        HR_OFFICER,
        DEPARMENT_MANAGER,
        PAYROLL_OFFICER,
        NORMAL_EMPLOYEE
    }

    private static final String EMPLOYEE_JSON_PATH = "/data/employeeList.json";

    // Account information
    private String username;
    private String password;

    public final String getUsername() {
        return this.username;
    }

    public final void setUsername(final String username) {
        this.username = username;
    }

    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(final String password) {
        this.password = password;
    }

    // Personal information
    private String name;
    private String gender;
    private String passport;
    private String identificationCard;
    private String phoneNumber;
    private String birthDate;
    private String email;
    private String address;
    private String emergencyContact;

    public final String getName() {
        return this.name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getGender() {
        return this.gender;
    }

    public final void setGender(final String gender) {
        this.gender = gender;
    }

    public final String getPassport() {
        return this.passport;
    }

    public final void setPassport(final String passport) {
        this.passport = passport;
    }

    public final String getIdentificationCard() {
        return this.identificationCard;
    }

    public final void setIdentificationCard(final String identificationCard) {
        this.identificationCard = identificationCard;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public final String getBirthDate() {
        return this.birthDate;
    }

    public final void setBirthDate(final String birthDate) {
        this.birthDate = birthDate;
    }

    public final String getEmail() {
        return this.email;
    }

    public final void setEmail(final String email) {
        this.email = email;
    }

    public final String getAddress() {
        return this.address;
    }

    public final void setAddress(final String address) {
        this.address = address;
    }

    public final String getEmergencyContact() {
        return this.emergencyContact;
    }

    public final void setEmergencyContact(final String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    // Job-related information
    private String role;
    private String position;
    public Department department;

    public final String getRole() {
        return this.role;
    }

    public final void setRole(final String role) {
        this.role = role;
    }

    public final String getPosition() {
        return this.position;
    }

    public final void setPosition(final String position) {
        this.position = position;
    }

    // Additional details
    public Schedule schedule;
    public Payroll payroll;

    @Override
    public String toString() {
        return String.format("%s %s", this.role, this.name);
    }

    // Method to find an employee by username
    public static Employee findByUsername(String username) throws Exception {
        Gson gson = new Gson();
        try (InputStream inputStream = Employee.class.getResourceAsStream(EMPLOYEE_JSON_PATH)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + EMPLOYEE_JSON_PATH);
            }
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                Type userType = new TypeToken<Map<String, Employee>>() {}.getType();
                Map<String, Employee> employees = gson.fromJson(reader, userType);
                Employee output = employees.get(username);
                if (output == null) {
                    throw new Exception("User with username " + username + " is not found");
                }
                output.payroll = Payroll.findByUsername(username);
                return output;
            }
        }
    }
}
