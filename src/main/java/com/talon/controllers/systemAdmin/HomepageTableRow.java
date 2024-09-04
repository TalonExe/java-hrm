package com.talon.controllers.systemAdmin;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class HomepageTableRow {
    private final SimpleIntegerProperty number;
    private final SimpleStringProperty username;
    private final SimpleStringProperty role;
    private final Button accountStatusButton;

    public HomepageTableRow(int number, String username, String role, Button accountStatusButton) {
        this.number = new SimpleIntegerProperty(number);
        this.username = new SimpleStringProperty(username);
        this.role = new SimpleStringProperty(role);
        this.accountStatusButton = accountStatusButton;
    }

    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public Button getAccountStatusButton() {
        return accountStatusButton;
    }
}