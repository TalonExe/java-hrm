package com.talon.components.SystemAdmin;

import com.talon.models.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.talon.utils.EmployeeUtils;
import javafx.scene.control.Alert;

public class EditEmployeeModal {
    @FXML
    private TextField usernameField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    private Employee employee;

    public void setEmployeeData(Employee employee) {
        this.employee = employee;
        usernameField.setText(employee.getUsername());
        roleComboBox.setValue(employee.getRole());
    }

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
        // Validate and save changes
        String newPassword = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        if (newPassword.equals(confirmPassword) && !newPassword.isEmpty() && !confirmPassword.isEmpty()) {
            // Update employee details
            employee.setRole(roleComboBox.getValue());
            if (!newPassword.isEmpty()) {
                String hashedPassword = EmployeeUtils.hashPassword(newPassword);
                employee.setPassword(hashedPassword);
            }
            // Save changes to the employee list
            EmployeeUtils.WriteData(EmployeeUtils.ReadData()); // Update this to save the modified employee
            closeModal();
        } else {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match");
            alert.showAndWait();
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
