package com.talon.models;

public class SessionState {

    private static SessionState instance;
    private Employee employee;

    private SessionState(){

    }

    public static SessionState getInstance(){
        if (instance == null){
            instance = new SessionState();
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
