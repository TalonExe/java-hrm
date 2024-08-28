package com.talon.controllers;

import java.io.IOException;

import com.talon.Router;
import com.talon.models.Employee;
import com.talon.models.LoggedInEmployee;
import com.talon.models.UpdatableController;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class EmployeeController implements UpdatableController {
    private final Router route = Router.getInstance();

    @FXML
    private TextField loginUsername;

    @FXML
    private TextField loginPassword;

    @FXML
    private TextField newPassword;

    @FXML
    private TextField confirmNewPassword;

    @FXML
    private void switchToLogin() throws Exception {
        route.switchToScene("LoginPage");
    }

    /*
     * TO DO:
     * Implement error handling and error message
     * username should be lowercase
     * Implement password decryption
     * Implement user details chaching
     * Implement attendance tracking
     * Change Scenes according to user roles
    */
    @FXML
    private void loginProcess() throws IOException {
        String usernameInput = loginUsername.getText();
        String passwordInput = loginPassword.getText();

        try {
            Employee loggedInEmployee = Employee.findByUsername(usernameInput);
            LoggedInEmployee.getInstance().setEmployee(loggedInEmployee);

            System.out.println(loggedInEmployee);
            if (loggedInEmployee.getPassword().equals(passwordInput)) {
                if (loggedInEmployee.getRole().equals("HR")) {
                    route.switchToScene("MainLobbyHR");
                } else {
                    route.switchToScene("EmployeePersonal");
                }
            } else {
                System.out.println("Invalid username or password");
            }
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private void switchToPasswordChange() throws IOException {
        route.switchToScene("PasswordChange");
    }

    /*
     * TO DO:
     * Add username field
     * Implement Password Validation
     * Implement Password Encryption
    */
    @FXML
    private void resetPassword() throws IOException {
        String passwordInput = newPassword.getText();
        String confirmPasswordInput = confirmNewPassword.getText();
        if (passwordInput.equals(confirmPasswordInput)) {
            route.switchToScene("PasswordConfirmation");
        }
    }

    /*
     * TO DO:
     * Fetch user data
    */
    @FXML
    private TextField employeeMonthlySalary;

    @FXML
    private void switchToEmployeeSalary() throws IOException {
        route.switchToScene("EmployeeSalary");
    }

    /*
     * TO DO:
     * Fetch user data
    */
    @FXML
    private void switchToEmployeeProfile() throws IOException {
        route.switchToScene("EmployeeProfile");
    }

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField addressField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private TextField contactNumberField;

    @FXML
    private TextField emergencyContactField;

    @FXML
    private TextField emailField;

    @FXML
    private void switchToEmployeePersonal() throws IOException {
        route.switchToScene("EmployeePersonal");
    }

    /*
     * TO DO:
     * Implement Attendance Checkout
     * Clear user details chache : DONE
    */
    @FXML
    private void logOut() throws Exception {
        LoggedInEmployee.getInstance().setEmployee(null);
        switchToLogin();
    }

    @FXML
    private void exit() throws Exception {
        System.exit(0);
    }

    @Override
    public void updateUI() {
        Employee currentEmployee = LoggedInEmployee.getInstance().getEmployee();
        //setup for employee payroll
        if (route.getCurrentSceneName().equals("EmployeeSalary") && currentEmployee != null) {
            employeeMonthlySalary.setText(String.format("RM %.2f", currentEmployee.payroll.getEmployeeSalary()));
        }
    }

}
