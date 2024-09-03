package com.talon.utils;

public class SessionState {
    private static SessionState instance;
    private String loggedInUserId;

    private SessionState() {
        // Private constructor to prevent instantiation
    }

    public static SessionState getInstance() {
        if (instance == null) {
            instance = new SessionState();
        }
        return instance;
    }

    public String getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(String userId) {
        this.loggedInUserId = userId;
    }

    public void clearSession() {
        loggedInUserId = null;
    }
}
