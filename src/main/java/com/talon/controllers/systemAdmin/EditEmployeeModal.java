package com.talon.controllers.systemAdmin;

import com.talon.models.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.talon.utils.EmployeeUtils;
import java.util.Map;
import com.talon.controllers.BaseModal;

public class EditEmployeeModal extends BaseModal {
    @FXML
    private TextField usernameField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    private Employee employee;
    private String employeeId;

    public void setEmployeeData(String employeeId, Employee employee) {
        this.employeeId = employeeId;
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
        // Validate username length
        String username = usernameField.getText();
        if (username.length() < 4) {
            showErrorAlert("Username must be at least 4 characters long");
            return;
        }
        employee.setRole(roleComboBox.getValue());
        employee.setUsername(usernameField.getText());

        // Validate and save changes
        String newPassword = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        Map<String, Employee> employees = EmployeeUtils.ReadData();
        
        if (newPassword.equals(confirmPassword) && !newPassword.isEmpty() && !confirmPassword.isEmpty()) {
            if (newPassword.length() < 8) {
                showErrorAlert("Password must be at least 8 characters long");
                return;
            }
            String hashedPassword = EmployeeUtils.hashPassword(newPassword);
            employee.setPassword(hashedPassword);
            
            employees.put(employeeId, employee);
            EmployeeUtils.WriteData(employees);
            closeModal();
        } else if (newPassword.isEmpty() && confirmPassword.isEmpty()) {
            employees.put(employeeId, employee);
            EmployeeUtils.WriteData(employees);
            closeModal();
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
