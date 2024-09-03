package com.talon.controllers.hr;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class ManageEmploymentDetailsRow {
    private final SimpleIntegerProperty number;
    private final SimpleStringProperty username;
    private final SimpleStringProperty fullName;
    private final SimpleStringProperty role;
    private final SimpleStringProperty position;
    private final SimpleStringProperty department;
    private final Button viewMore;

    public ManageEmploymentDetailsRow(int number, String username, String fullName, String role, String position, String department, Button viewMore) {
        this.number = new SimpleIntegerProperty(number);
        this.username = new SimpleStringProperty(username);
        this.fullName = new SimpleStringProperty(fullName);
        this.role = new SimpleStringProperty(role);
        this.position = new SimpleStringProperty(position);
        this.department = new SimpleStringProperty(department);
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

    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public Button getViewMore() {
        return viewMore;
    }    
}
