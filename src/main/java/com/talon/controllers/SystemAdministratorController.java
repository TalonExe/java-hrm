package com.talon.controllers;

import com.talon.Router;
import com.talon.utils.EmployeeUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SystemAdministratorController extends EmployeeController {
    private final Router route = Router.getInstance();

    //add user fields
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField passportNoField;

    @FXML
    private TextField icNoField;

    @FXML
    private TextField phoneNoField;

    @FXML
    private DatePicker birthdayPicker;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emergencyContactField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField positionField;

    @FXML
    private Button createUserButton;

    @FXML
    private Label statusLabel;

    @FXML
    private void switchToAddUser(ActionEvent event) {
        route.switchToScene("AddUser");
    }

    @FXML
    private void handleAddUser(ActionEvent event) {
        // Logic to add a user
        String username = usernameField.getText();
        String password = passwordField.getText();
        String gender = genderComboBox.getValue();
        String passportNo = passportNoField.getText();
        String icNo = icNoField.getText();
        String phoneNo = phoneNoField.getText();
        String birthday = (birthdayPicker.getValue() != null) ? birthdayPicker.getValue().toString() : "";
        String email = emailField.getText();
        String address = addressField.getText();
        String emergencyContact = emergencyContactField.getText();
        String role = roleComboBox.getValue();
        String position = positionField.getText();

        try {
            EmployeeUtils.createEmployee(username, password, role, gender, passportNo, icNo, phoneNo, birthday, email, address, emergencyContact, role, position);
            System.out.println(EmployeeUtils.ReadData());
        } catch (Exception ex) {
        }
        clearFields();
        showAlert("Add User", "This would open the Add User dialog.");
    }

    @FXML
    private void handleUpdateUser(ActionEvent event) {
        // Logic to update a user
        showAlert("Update User", "This would open the Update User dialog.");
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        // Logic to delete a user
        showAlert("Delete User", "This would open the Delete User dialog.");
    }

    @FXML
    private void handleViewUsers(ActionEvent event) {
        // Logic to view users
        showAlert("View Users", "This would display the list of users.");
    }

    @FXML
    private void handleUnlockAccounts(ActionEvent event) {
        // Logic to unlock accounts
        showAlert("Unlock Accounts", "This would unlock user accounts.");
    }

    @FXML
    private void handleSettings(ActionEvent event) {
        // Logic to open settings
        showAlert("System Settings", "This would open the System Settings dialog.");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        // Logic to log out
        showAlert("Logout", "This would log the user out.");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void updateUI() {
        if (route.getCurrentSceneName().equals("AddUser")) {
            genderComboBox.getItems().addAll("Male", "Female", "Other");
            roleComboBox.getItems().addAll("System Administrator", "Employee", "HR Manager", "Department Manager", "Payroll Manager");
        }
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        genderComboBox.getSelectionModel().clearSelection();
        passportNoField.clear();
        icNoField.clear();
        phoneNoField.clear();
        birthdayPicker.setValue(null);
        emailField.clear();
        addressField.clear();
        emergencyContactField.clear();
        roleComboBox.getSelectionModel().clearSelection();
        positionField.clear();
    }

    
}