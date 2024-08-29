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

    //account info
    private String username;

    public final String getUsername() {
        return this.username;
    }

    public final void setUsername(final String username){
        this.username = username;
    }

    private String password;

    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(final String password) {
        this.password = password;
    }
    
    //personal info
    private String name;

    public final String getName() {
        return this.name;
    }

    public final void setName(final String name){
        this.name = name;
    }

    private String gender;

    public final String getGender() {
        return this.gender;
    }

    public final void setGender(final String gender){
        this.gender = gender;
    }

    private String passport;

    public final String getPassport() {
        return this.passport;
    }

    public final void setPassport(final String passport){
        this.passport = passport;
    }

    private String identificationCard;

    public final String getIdentificationCard() {
        return this.identificationCard;
    }

    public final void setIdentificationCard(final String idenficationCard){
        this.identificationCard = idenficationCard;
    }

    private String phoneNumber;

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final void setPhoneNumber(final String username){
        this.phoneNumber = username;
    }

    private String birthDate;

    public final String getBirthDate() {
        return this.birthDate;
    }

    public final void setBirthDate(final String birthDate){
        this.birthDate = birthDate;
    }

    private String email;

    public final String getEmail() {
        return this.email;
    }

    public final void setEmail(final String email){
        this.email = email;
    }

    private String address;

    public final String getAddress() {
        return this.address;
    }

    public final void setAddress(final String address){
        this.address = address;
    }

    //emergency contact
    private String emergencyContact;

    public final String getEmergencyContact() {
        return this.emergencyContact;
    }

    public final void setEmergencyContact(final String emergencyProperty){
        this.emergencyContact = emergencyProperty;
    }

    //working experience

    //role, position, department
    private String role; 

    public final String getRole() {
        return this.role;
    }

    public final void setRole(final String role){
        this.role = role;
    }

    private String position;

    public final String getPosition() {
        return this.position;
    }

    public final void setPosition(final String position){
        this.position = position;
    }

    public Department department;

    //History of salary increment and change of position

    //overview of leave entitlenent
    public Schedule schedule;
    
    //monthly gross salary
    public Payroll payroll;

    @Override
    public String toString() {
        return String.format("%s %s",this.role, this.name);
    }
    // Todo: modify to fetch payroll data from payrollDetails.json
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

    public static void createEmployee() throws Exception {
        
    }
}
