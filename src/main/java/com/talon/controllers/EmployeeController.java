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
    private TextField employeeMonthlySalary;
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
    private void switchToLogin() throws Exception {
        route.switchToScene("LoginPage");
    }

    @FXML
    private void loginProcess() throws IOException {
        String usernameInput = loginUsername.getText();
        String passwordInput = loginPassword.getText();

        try {
            Employee loggedInEmployee = Employee.findByUsername(usernameInput);
            LoggedInEmployee.getInstance().setEmployee(loggedInEmployee);

            if (loggedInEmployee.getPassword().equals(passwordInput)) {
                clearLoginFields();
                switchSceneBasedOnRole(loggedInEmployee.getRole());
            } else {
                handleLoginError("Invalid username or password");
            }
        } catch (Exception e) {
            handleLoginError(e.getMessage());
        }
    }

    private void switchSceneBasedOnRole(String role) throws IOException {
        switch (role) {
            case "HR":
                route.switchToScene("MainLobbyHR");
                break;
            case "Payroll Manager":
                route.switchToScene("PayrollLobby");
                break;
            default:
                route.switchToScene("EmployeePersonal");
                break;
        }
    }

    private void clearLoginFields() {
        loginUsername.setText("");
        loginPassword.setText("");
    }

    private void handleLoginError(String message) {
        System.err.println(message);  // Replace with actual user feedback
    }

    @FXML
    private void switchToPasswordChange() throws IOException {
        route.switchToScene("PasswordChange");
    }

    @FXML
    private void resetPassword() throws IOException {
        String passwordInput = newPassword.getText();
        String confirmPasswordInput = confirmNewPassword.getText();
        if (passwordInput.equals(confirmPasswordInput)) {
            // TODO: Implement Password Encryption
            route.switchToScene("PasswordConfirmation");
        } else {
            // TODO: Show error message
        }
    }

    @FXML
    private void switchToEmployeeSalary() throws IOException {
        route.switchToScene("EmployeeSalary");
    }

    @FXML
    private void switchToEmployeeProfile() throws IOException {
        route.switchToScene("EmployeeProfile");
    }

    @FXML
    private void switchToEmployeePersonal() throws IOException {
        route.switchToScene("EmployeePersonal");
    }

    @FXML
    private void switchToApplyLeave() throws IOException {
        route.switchToScene("ApplyLeave");
    }

    @FXML
    private void logOut() throws Exception {
        LoggedInEmployee.getInstance().setEmployee(null);
        route.switchToScene("LoginPage");
    }

    @FXML
    private void exit() throws Exception {
        System.exit(0);
    }

    @Override
    public void updateUI() {
        Employee currentEmployee = LoggedInEmployee.getInstance().getEmployee();

        if (currentEmployee == null) return;

        String currentScene = route.getCurrentSceneName();

        switch (currentScene) {
            case "EmployeeSalary":
                updateSalaryUI(currentEmployee);
                break;
            case "EmployeePersonal":
                updatePersonalUI(currentEmployee);
                break;
            case "EmployeeProfile":
                updateProfileUI(currentEmployee);
                break;
            default:
                break;
        }
    }

    private void updateSalaryUI(Employee employee) {
        employeeMonthlySalary.setText(String.format("RM %.2f", employee.payroll.getEmployeeSalary()));
    }

    private void updatePersonalUI(Employee employee) {
        nameField.setText(employee.getName());
        selectGender(employee.getGender());
        setAgeAndDOB(employee.getBirthDate());
        addressField.setText(employee.getAddress());
        contactNumberField.setText(employee.getPhoneNumber());
        emergencyContactField.setText(employee.getEmergencyContact());
        emailField.setText(employee.getEmail());

        disablePersonalFields();
    }

    private void selectGender(String gender) {
        if ("Male".equals(gender)) {
            genderGroup.selectToggle(maleRadioButton);
        } else {
            genderGroup.selectToggle(femaleRadioButton);
        }
    }

    private void setAgeAndDOB(String birthDateStr) {
        LocalDate birthDate = LocalDate.parse(birthDateStr);
        Period period = Period.between(birthDate, LocalDate.now());
        ageField.setText(String.valueOf(period.getYears()));
        dobPicker.setValue(birthDate);
    }

    private void disablePersonalFields() {
        nameField.setDisable(true);
        addressField.setDisable(true);
        ageField.setDisable(true);
        dobPicker.setDisable(true);
        contactNumberField.setDisable(true);
        emergencyContactField.setDisable(true);
        emailField.setDisable(true);
        maleRadioButton.setDisable(true);
        femaleRadioButton.setDisable(true);
    }

    private void updateProfileUI(Employee employee) {
        roleLabel.setText(employee.getRole());
        departmentLabel.setText("smtg"); // TODO: Fetch actual department
        positionLabel.setText(employee.getPosition());
        workExperienceLabel.setText("work 1"); // TODO: Fetch actual work experience
        workExperienceLabel2.setText("work 2"); // TODO: Fetch actual work experience
    }
}