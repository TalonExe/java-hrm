package com.talon.components.SystemAdmin;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class UserManagementRow {
    private SimpleIntegerProperty number;
    private SimpleStringProperty username;
    private SimpleStringProperty role;
    private Button editButton;
    private Button disableButton;

    public UserManagementRow(int number, String username, String role, Button editButton, Button disableButton) {
        this.number = new SimpleIntegerProperty(number);
        this.username = new SimpleStringProperty(username);
        this.role = new SimpleStringProperty(role);
        this.editButton = editButton;
        this.disableButton = disableButton;
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
    
    public Button getEditButton() {
        return editButton;
    }

    public Button getDisableButton() {
        return disableButton;
    }
}

