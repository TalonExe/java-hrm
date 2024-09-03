package com.talon.models;

public class EmergencyContact {
    private String name;
    private String phoneNumber;
    private String relationship;

    public EmergencyContact(String name, String phoneNumber, String relationship) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.relationship = relationship;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getRelationship() {
        return relationship;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}