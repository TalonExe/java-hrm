package com.talon.models;

public abstract class Employee {

    public enum Roles {
       NORMAL_EMPLOYEE,
       HR_EMPLOYEE,
       PAYROLL_EMPLOYEE,
       DEPARTMENT_MANAGER
    }

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

    int attempts = 3;

    //personal info
    private String name;

    public final String getName() {
        return this.name;
    }

    public final void setName(final String name){
        this.name = name;
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

    Department department;

    //History of salary increment and change of position

    //overview of leave entitlenent
    Schedule schedule;
    
    //monthly gross salary
    Payroll payroll;

    @Override
    public String toString() {
        return String.format("%s %s",this.role, this.name);
    }
}
