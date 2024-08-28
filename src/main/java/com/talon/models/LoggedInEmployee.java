package com.talon.models;

public class LoggedInEmployee {

    private static LoggedInEmployee instance;
    private Employee employee;

    private LoggedInEmployee(){

    }

    public static LoggedInEmployee getInstance(){
        if (instance == null){
            instance = new LoggedInEmployee();
        }
        return instance;
    }

    public Employee getEmployee(){
        return employee;
    }

    public void setEmployee(Employee employee){
        this.employee = employee;
        }
}
