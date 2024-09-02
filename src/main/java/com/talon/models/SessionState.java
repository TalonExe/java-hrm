package com.talon.models;

public class SessionState {

    // Volatile ensures visibility of changes to instance across threads
    private static volatile SessionState instance;
    private Employee employee;

    // Private constructor to prevent instantiation
    private SessionState() {}

    // Double-checked locking for thread-safe lazy initialization
    public static SessionState getInstance() {
        if (instance == null) {
            synchronized (SessionState.class) {
                if (instance == null) {
                    instance = new SessionState();
                }
            }
        }
        return instance;
    }

    // Getter for Employee
    public Employee getEmployee() {
        return employee;
    }

    // Setter for Employee
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}