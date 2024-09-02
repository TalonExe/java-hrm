package com.talon.models;

public class Employee {
    // Attributes
    private String username;
    private String password;
    private String name;
    private String gender;
    private String passport;
    private String identificationCard;
    private String phoneNumber;
    private String birthDate;
    private String email;
    private String address;
    private String emergencyContact;
    private String role;
    private String position;
    private Integer loginAttempts;
    private String accountStatus;
    private boolean accountDisabled;

    // Constructor
    public Employee(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
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
    public final void setIdentificationCard(final String idenficationCard) {
        this.identificationCard = idenficationCard;
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
    public final void setEmergencyContact(final String emergencyProperty) {
        this.emergencyContact = emergencyProperty;
    }

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

    public Integer getLoginAttempts() {
        return loginAttempts;
    }
    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public String getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public boolean getAccountDisabled() {
        return accountDisabled;
    }
    public void setAccountDisabled(boolean accountDisabled) {
        this.accountDisabled = accountDisabled;
    }

    // Class Methods
    @Override
    public String toString() {
        return String.format("%s %s", this.role, this.name);
    }
}