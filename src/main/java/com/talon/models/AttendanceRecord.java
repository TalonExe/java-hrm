package com.talon.models;

public class AttendanceRecord {
    private String date;
    private String status;
    private String timeIn;
    private String timeOut;

    public AttendanceRecord(String date, String status, String timeIn, String timeOut) {
        this.date = date;
        this.status = status;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }
}
