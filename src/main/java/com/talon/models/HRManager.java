package com.talon.models;

public class HRManager extends Employee{
    public HRManager(String username, String password, String name, String passport, String identificationCard, 
                    String phoneNumber, String birthDate, String email,  String address, String emergencyContact, 
                    String role, String position){

        super(username, password, name, passport, identificationCard, phoneNumber, 
            birthDate, email, address, emergencyContact, role, position);
    }
}
