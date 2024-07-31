package com.talon.controllers;

import java.io.IOException;
import com.talon.Router;
import com.talon.models.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EmployeeController {
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
     * username should be lowecase
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
            Employee employee = Employee.findByUsername(usernameInput);
            if (employee.getPassword().equals(passwordInput)) {
                if (employee.getRole().equals("HR")) {
                    route.switchToScene("MainLobbyHR");
                } else {
                    route.switchToScene("MainLobbyEmployee");
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

    /*
     * TO DO:
     * Implement Attendance Checkout
     * Clear user details chache
    */
    @FXML
    private void logOut() throws Exception {
        switchToLogin();
    }

    @FXML
    private void exit() throws Exception {
        System.exit(0);
    }
}
