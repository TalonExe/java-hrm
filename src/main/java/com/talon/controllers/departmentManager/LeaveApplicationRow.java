package com.talon.controllers.departmentManager;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class LeaveApplicationRow {
    private final SimpleIntegerProperty number;
    private final SimpleStringProperty employeeName;
    private final SimpleStringProperty leaveType;
    private final SimpleStringProperty startDate;
    private final SimpleStringProperty endDate;
    private final SimpleStringProperty reason;
    private final SimpleStringProperty status;
    private final Button actionButton;
    private final Button notifyButton;

    public LeaveApplicationRow(int number, String employeeName, String leaveType, String startDate, String endDate, String reason, String status, Button actionButton, Button notifyButton) {
        this.number = new SimpleIntegerProperty(number);
        this.employeeName = new SimpleStringProperty(employeeName);
        this.leaveType = new SimpleStringProperty(leaveType);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        this.reason = new SimpleStringProperty(reason);
        this.status = new SimpleStringProperty(status);
        this.actionButton = actionButton;
        this.notifyButton = notifyButton;
    }
    
    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public String getEmployeeName() {
        return employeeName.get();
    }

    public SimpleStringProperty employeeNameProperty() {
        return employeeName;
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
    
    public Button getActionButton() {
        return actionButton;
    }
    
    public Button getNotifyButton() {
        return notifyButton;
    }
}
