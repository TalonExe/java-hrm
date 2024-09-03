package com.talon.models;

import java.time.LocalDate;

public class Leave {
    
    public enum LeaveType {
        ANNUAL_LEAVE,
        MEDICAL_LEAVE,
        UNPAID_LEAVE,
        MATERNITY_LEAVE;
    }

    public enum LeaveStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

    private String employeeId;
    private LeaveType leaveType;
    private String startDate;
    private String endDate;
    private String reason;
    private LeaveStatus status;

    // Constructor
    public Leave( String employeeId, LeaveType leaveType, LocalDate startDate, LocalDate endDate, String reason) {
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.startDate = startDate.toString();
        this.endDate = endDate.toString();
        this.reason = reason;
        this.status = LeaveStatus.PENDING; // Default status when a leave is applied
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getStartDate() {
        return LocalDate.parse(startDate);
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate.toString();
    }

    public LocalDate getEndDate() {
        return LocalDate.parse(endDate);
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate.toString();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    // Method to cancel the leave application
    public void cancelLeave() {
        if (this.status == LeaveStatus.PENDING) {
            this.status = LeaveStatus.REJECTED;
        }
    }

    // Approve the leave
    public void approveLeave() {
        if (this.status == LeaveStatus.PENDING) {
            this.status = LeaveStatus.APPROVED;
        }
    }

    // Reject the leave
    public void rejectLeave() {
        if (this.status == LeaveStatus.PENDING) {
            this.status = LeaveStatus.REJECTED;
        }
    }
    
    @Override
    public String toString() {
        return String.format("Leave [employeeId=%s, leaveType=%s, startDate=%s, endDate=%s, reason=%s, status=%s]",
                employeeId, leaveType, startDate, endDate, reason, status);
    }
}

