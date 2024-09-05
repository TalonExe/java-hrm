package com.talon.controllers.systemAdmin;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FeedbackTableRow {
    private final SimpleIntegerProperty number;
    private final SimpleStringProperty userType;
    private final SimpleStringProperty date;
    private final SimpleStringProperty feedback;

    public FeedbackTableRow(int number, String userType, String date, String feedback) {
        this.number = new SimpleIntegerProperty(number);
        this.userType = new SimpleStringProperty(userType);
        this.date = new SimpleStringProperty(date);
        this.feedback = new SimpleStringProperty(feedback);
    }

    public int getNumber() {
        return number.get();
    }

    public String getUserType() {
        return userType.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getFeedback() {
        return feedback.get();
    }
}
