package com.talon.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import com.talon.Router;
import com.talon.models.Employee;
import com.talon.models.LoggedInEmployee;
import com.talon.models.UpdatableController;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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
                loginUsername.setText("");
                loginPassword.setText("");
                switch (loggedInEmployee.getRole()) {
                    case "HR":
                        route.switchToScene("MainLobbyHR");
                        break;
                    case "Payroll Manager":
                        // route.switchToScene("Payroll_Lobby");
                        break;
                    default:
                        route.switchToScene("EmployeePersonal");
                        break;
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
    private TextField roleLabel;

    @FXML
    private TextField departmentLabel;

    @FXML
    private TextField positionLabel;

    @FXML
    private TextField workExperienceLabel;

    @FXML
    private TextField workExperienceLabel2;

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
    private ToggleGroup genderGroup;

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

    @FXML private void switchToApplyLeave() throws IOException {
        route.switchToScene("ApplyLeave");
    }

    /*
     * TO DO:
     * Implement Attendance Checkout
     * Clear user details chache : DONE
    */
    @FXML
    private void logOut() throws Exception {
        LoggedInEmployee.getInstance().setEmployee(null);
        route.switchToScene("LoginPage");
    }

    @FXML
    private void exit() throws Exception {
        System.exit(0);
    }

    //update ui too bloated find some way to alleviate the pain of having to read this, split rendering into functions based on pages???

    @Override
    public void updateUI() {
        Employee currentEmployee = LoggedInEmployee.getInstance().getEmployee();
        
        //setup for employee payroll
        //profile stuff todo: if not in edit mode disable text fields
        if (route.getCurrentSceneName().equals("EmployeeSalary") && currentEmployee != null) {
            employeeMonthlySalary.setText(String.format("RM %.2f", currentEmployee.payroll.getEmployeeSalary()));
        }
        else if (route.getCurrentSceneName().equals("EmployeePersonal") && currentEmployee != null) {

            nameField.setText(currentEmployee.getName());

            if (currentEmployee.getGender().equals("Male")) {
                genderGroup.selectToggle(maleRadioButton);
            } else {
                genderGroup.selectToggle(femaleRadioButton);
            } 
            LocalDate birthDate = LocalDate.parse(currentEmployee.getBirthDate());
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(birthDate, currentDate);

            addressField.setText(currentEmployee.getAddress());
            ageField.setText(String.format("%d", period.getYears()));
            dobPicker.setValue(birthDate);
            contactNumberField.setText(currentEmployee.getPhoneNumber());
            emergencyContactField.setText(currentEmployee.getEmergencyContact());
            emailField.setText(currentEmployee.getEmail());

            nameField.setDisable(true);
            addressField.setDisable(true);
            ageField.setDisable(true);
            dobPicker.setDisable(true);
            contactNumberField.setDisable(true);
            emergencyContactField.setDisable(true);
            emailField.setDisable(true);
            maleRadioButton.setDisable(true);
            femaleRadioButton.setDisable(true);
        } else if(route.getCurrentSceneName().equals("EmployeeProfile") && currentEmployee != null) {
            roleLabel.setText(currentEmployee.getRole());
            departmentLabel.setText("smtg");
            positionLabel.setText(currentEmployee.getPosition());
            workExperienceLabel.setText("work 1");
            workExperienceLabel2.setText("work 2");
        }
    }

}
