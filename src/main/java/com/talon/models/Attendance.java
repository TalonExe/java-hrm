package com.talon.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private String username;
    private LocalDate date;
    private LocalTime clockIn;
    private LocalTime clockOut;
    private int lateDays;
    private double penalty;

    private static final LocalTime LATE_TIME = LocalTime.of(9, 30);
    private static final double PENALTY_AMOUNT = 100.0;

    public Attendance(String username, LocalDate date, LocalTime clockIn, LocalTime clockOut) {
        this.username = username;
        this.date = date;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.penalty = 0.0;
        this.lateDays = 0;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getClockIn() {
        return clockIn;
    }

    public LocalTime getClockOut() {
        return clockOut;
    }

    public int getLateDays() {
        return lateDays;
    }

    public double getPenalty() {
        return penalty;
    }

    public void checkLateness() {
        if (clockIn.isAfter(LATE_TIME)) {
            lateDays++;
        }
    }

    public void applyPenalty() {
        if (lateDays >= 3) {
            penalty = PENALTY_AMOUNT;
        }
    }
}