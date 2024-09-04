package com.talon.controllers.systemAdmin;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.talon.utils.EmployeeUtils;
import com.talon.models.Employee;
import com.talon.controllers.BaseModal;

public class CreateUserModal extends BaseModal {
    @FXML
    private TextField usernameField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void initialize() {
        roleComboBox.getItems().addAll(
            "System Administrator",
            "HR Officer",
            "Department Manager",
            "Payroll Officer",
            "Normal Employee"
        );
    }

    @FXML
    private void handleConfirm() {
        // Validate username length
        String role = roleComboBox.getValue();
        String username = usernameField.getText();
        if (username.length() < 4) {
            showErrorAlert("Username must be at least 4 characters long");
            return;
        }

        // Find if username already exists
        try {
            Employee employee = EmployeeUtils.findByUsername(username);
            if (employee != null) {
                showErrorAlert("Username already exists");
                return;
            }
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }

        if (role == null) {
            showErrorAlert("Role cannot be empty");
            return;
        }

        // Validate and save changes
        String newPassword = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        if (newPassword.equals(confirmPassword) && !newPassword.isEmpty() && !confirmPassword.isEmpty()) {
            if (newPassword.length() < 8) {
                showErrorAlert("Password must be at least 8 characters long");
                return;
            }
            try {
                EmployeeUtils.createEmployee(username, newPassword, role);
                closeModal();
            } catch (Exception e) {
                showErrorAlert(e.getMessage());
            }
        } else if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showErrorAlert("Password cannot be empty");
        } else {
            showErrorAlert("Passwords do not match");
        }
    }

    @FXML
    private void handleCancel() {
        closeModal();
    }

    private void closeModal() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}