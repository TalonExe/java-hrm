package com.talon.controllers.common;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class LeaveApplicationRow {
    private SimpleStringProperty leaveType;
    private SimpleStringProperty startDate;
    private SimpleStringProperty endDate;
    private SimpleStringProperty reason;
    private SimpleStringProperty status;
    private Button deleteButton;

        public LeaveApplicationRow(String leaveType, String startDate, String endDate, String reason, String status, Button deleteButton) {
        this.leaveType = new SimpleStringProperty(leaveType);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        this.reason = new SimpleStringProperty(reason);
        this.status = new SimpleStringProperty(status);
        this.deleteButton = deleteButton;
    }

    public String getLeaveType() {
            return leaveType.get();
    }

    public SimpleStringProperty leaveTypeProperty() {
        return leaveType;
    }

    public String getStartDate() {
        return startDate.get();
    }

    public SimpleStringProperty startDateProperty() {
        return startDate;
    }   

    public String getEndDate() {
        return endDate.get();
    }

    public SimpleStringProperty endDateProperty() {
        return endDate;
    }   

    public String getReason() {
        return reason.get();
    }

    public SimpleStringProperty reasonProperty() {
        return reason;
    }   

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }       

    public Button getDeleteButton() {
        return deleteButton;
    }
}
