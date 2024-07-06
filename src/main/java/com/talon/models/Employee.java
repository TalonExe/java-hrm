package com.talon.models;

public abstract class Employee {

    enum Roles {
       NORMAL_EMPLOYEE,
       HR_EMPLOYEE,
       PAYROLL_EMPLOYEE,
       DEPARTMENT_MANAGER
    }

    //account info
    String username, password;
    int attempts = 3;

    //personal info
    String name, passport, identificationCard, phoneNumber, birthDate, email, address;

    //emergency contact
    String emergencyContact;

    //working experience

    //role, position, department
    String role, position;
    Department department;

    //History of salary increment and change of position

    //overview of leave entitlenent
    Schedule schedule;
    
    //monthly gross salary
    Payroll payroll;

    public Employee(String username, String password, String name, String passport, String identificationCard, String phoneNumber, String birthDate, 
                    String email,  String address, String emergencyContact, String role, String position) {

        this.username = username;
        this.password = password;
        this.name = name;
        this.passport = passport;
        this.identificationCard = identificationCard;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.email = email;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.role = role;
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAttempts() {
        return attempts;
    }

    public String getName() {
        return name;
    }

    public String getPassport() {
        return passport;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public String getRole() {
        return role;
    }

    public String getPosition() {
        return position;
    }

    public Department getDepartment() {
        return department;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Payroll getPayroll() {
        return payroll;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return String.format("%s %s",this.role, this.name);
    }
}
