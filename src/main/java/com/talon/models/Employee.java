package com.talon.models;

import java.util.ArrayList;
import java.util.List;

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
    private List<Payroll> payrollRecords;
    private int annualLeaveBalance;
    private int medicalLeaveBalance;
    private int unpaidLeaveBalance;

    // Full Constructor
    public Employee(String username, String password, String name, String gender, String passport,
                    String identificationCard, String phoneNumber, String birthDate, String email,
                    String address, String emergencyContact, String role, String position,
                    Integer loginAttempts, String accountStatus, List<Payroll> payrollRecords) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.passport = passport;
        this.identificationCard = identificationCard;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.email = email;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.role = role;
        this.position = position;
        this.loginAttempts = loginAttempts;
        this.accountStatus = accountStatus;
        this.payrollRecords = payrollRecords;
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

    public List<Payroll> getPayrollRecords() {
        return payrollRecords;
    }

    public void setPayrollRecords(List<Payroll> payrollRecords) {
        this.payrollRecords = payrollRecords;
    }

    public int getAnnualLeaveBalance() {
        return annualLeaveBalance;
    }

    public void setAnnualLeaveBalance(int annualLeaveBalance) {
        this.annualLeaveBalance = annualLeaveBalance;
    }

    public int getMedicalLeaveBalance() {
        return medicalLeaveBalance;
    }

    public void setMedicalLeaveBalance(int medicalLeaveBalance) {
        this.medicalLeaveBalance = medicalLeaveBalance;
    }

    public int getUnpaidLeaveBalance() {
        return unpaidLeaveBalance;
    }

    public void setUnpaidLeaveBalance(int unpaidLeaveBalance) {
        this.unpaidLeaveBalance = unpaidLeaveBalance;
    }

    // Add a payroll record
    public void addPayrollRecord(Payroll payrollRecord) {
        if (this.payrollRecords == null) {
            this.payrollRecords = new ArrayList<>();
        }
        this.payrollRecords.add(payrollRecord);
    }

    // Get the latest payroll record
    public Payroll getLatestPayrollRecord() {
        if (this.payrollRecords == null || this.payrollRecords.isEmpty()) {
            return null;
        }
        return this.payrollRecords.get(this.payrollRecords.size() - 1);
    }

    // Get payroll record by date
    public Payroll getPayrollRecordByDate(String date) {
        if (this.payrollRecords == null || this.payrollRecords.isEmpty()) {
            return null;
        }
        for (Payroll record : this.payrollRecords) {
            if (record.getCreatedDate().toString().equals(date)) {
                return record;
            }
        }
        return null;
    }

    // Class Methods
    @Override
    public String toString() {
        return String.format("%s %s", this.role, this.name);
    }
}
