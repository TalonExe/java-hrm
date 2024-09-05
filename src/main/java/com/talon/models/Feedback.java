package com.talon.models;

public class Feedback {
    private String feedback;
    private String userType;
    private String date;

    public Feedback(String feedback, String userType, String date) {
        this.feedback = feedback;
        this.userType = userType;
        this.date = date;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getUserType() {
        return userType;
    }

    public String getDate() {
        return date;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
