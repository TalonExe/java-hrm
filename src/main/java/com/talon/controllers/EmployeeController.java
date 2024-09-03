package com.talon.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import com.talon.Router;
import com.talon.models.Employee;
import com.talon.models.SessionState;
import com.talon.utils.EmployeeUtils;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;

public class EmployeeController implements UpdatableController {
    private final Router route = Router.getInstance();

    @FXML
    private TextField loginUsername;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private void switchToLogin() throws Exception {
        route.switchToScene("LoginPage");
    }
    
    @FXML
    private void loginProcess() throws IOException {
        String usernameInput = loginUsername.getText();
        String passwordInput = loginPassword.getText();

        try {
            Employee loggedInEmployee = EmployeeUtils.findByUsername(usernameInput);
            if (loggedInEmployee == null) {
                ErrorAlert("Invalid username or password");
            } else if (loggedInEmployee.getAccountDisabled()) {
                ErrorAlert("Your account has been disabled");
            } else if (loggedInEmployee.getAccountStatus().equalsIgnoreCase("LOCKED")) {
                ErrorAlert("Your account has been locked");
            } else if (EmployeeUtils.verifyPassword(passwordInput, loggedInEmployee.getPassword())) {
                loginUsername.setText("");
                loginPassword.setText("");
                EmployeeUtils.updateLoginStatus(usernameInput, "SUCCESS");
                SessionState.getInstance().setEmployee(loggedInEmployee);
                switch (loggedInEmployee.getRole()) {
                    case "HR Officer":
                        route.switchToScene("MainLobbyHR");
                        ((HROController) route.getCurrentController()).refreshLobbyTable();
                        break;
                    case "Payroll Manager":
                        // route.switchToScene("Payroll_Lobby");
                        break;
                    case "System Administrator":
                        route.switchToScene("SystemAdminHomepage");
                        ((SystemAdministratorController) route.getCurrentController()).refreshHomepage();
                        break;
                    default:
                        route.switchToScene("EmployeePersonal");
                        break;
                }
            } else {
                ErrorAlert("Invalid username or password");
                EmployeeUtils.updateLoginStatus(usernameInput, "FAILED");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            ErrorAlert("Invalid username or password");
        }
    }

    @FXML
    private void switchToPasswordChange() throws IOException {
        route.switchToScene("PasswordChange");
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

    @FXML
    private void switchToApplyLeave() throws IOException {
        route.switchToScene("ApplyLeave");
    }

    /*
     * TO DO:
     * Implement Attendance Checkout
     * Clear user details chache : DONE
     */
    @FXML
    protected void logOut() throws Exception {
        SessionState.getInstance().setEmployee(null);
        route.switchToScene("LoginPage");
    }

    @FXML
    private void exit() throws Exception {
        System.exit(0);
    }

    protected void ErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    protected void WarningAlert(String message) {
    };

    // update ui too bloated find some way to alleviate the pain of having to read
    // this, split rendering into functions based on pages???
    @Override
    public void updateUI() {
        Employee currentEmployee = SessionState.getInstance().getEmployee();

        // setup for employee payroll
        // profile stuff todo: if not in edit mode disable text fields
        if (route.getCurrentSceneName().equals("EmployeeSalary") && currentEmployee != null) {
            // employeeMonthlySalary.setText(String.format("RM %.2f",
            // currentEmployee.payroll.getEmployeeSalary()));
        } else if (route.getCurrentSceneName().equals("EmployeePersonal") && currentEmployee != null) {

            nameField.setText(currentEmployee.getFullName());

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
            // emergencyContactField.setText(currentEmployee.getEmergencyContact());
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
        } else if (route.getCurrentSceneName().equals("EmployeeProfile") && currentEmployee != null) {
            roleLabel.setText(currentEmployee.getRole());
            departmentLabel.setText("smtg");
            positionLabel.setText(currentEmployee.getPosition());
            workExperienceLabel.setText("work 1");
            workExperienceLabel2.setText("work 2");
        }
    }
}
