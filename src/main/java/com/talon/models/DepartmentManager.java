package com.talon.models;

public class DepartmentManager extends Employee{


    public DepartmentManager(String username, String password, String name, String passport, String identificationCard, String phoneNumber, String birthDate, 
                    String email,  String address, String emergencyContact, String position){

        super(username, password, name, passport, identificationCard, phoneNumber, birthDate, email, address, emergencyContact, "Department Manager", position);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
