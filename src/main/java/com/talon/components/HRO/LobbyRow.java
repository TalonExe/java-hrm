package com.talon.components.HRO;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class LobbyRow {
    private final SimpleIntegerProperty number;
    private final SimpleStringProperty username;
    private final SimpleStringProperty fullName;
    private final SimpleStringProperty role;
    private Button viewMore;

    public LobbyRow(int number, String username, String fullName, String role, Button viewMore) {
        this.number = new SimpleIntegerProperty(number);
        this.username = new SimpleStringProperty(username);
        this.fullName = new SimpleStringProperty(fullName);
        this.role = new SimpleStringProperty(role);
        this.viewMore = viewMore;
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
    
    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }
    
    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }
    
    public Button getViewMore() {
        return viewMore;
    }
}