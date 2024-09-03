package com.talon.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import com.talon.Router;
import com.talon.models.Employee;
import com.talon.models.Leave;
import com.talon.models.SessionState;
import com.talon.utils.EmployeeUtils;
import com.talon.utils.LeaveUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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
            Employee loggedInEmployee = EmployeeUtils.findByUsername(usernameInput);
            if (loggedInEmployee == null) {
                ErrorAlert("Invalid username or password");
            } else if (loggedInEmployee.getAccountStatus().equalsIgnoreCase("LOCKED")) {
                ErrorAlert("Your account has been locked");
            } else if (EmployeeUtils.verifyPassword(passwordInput, loggedInEmployee.getPassword())) {
                loginUsername.setText("");
                loginPassword.setText("");
                EmployeeUtils.updateLoginStatus(usernameInput, "SUCCESS");
                SessionState.getInstance().setEmployee(loggedInEmployee);
                switch (loggedInEmployee.getRole()) {
                    case "HR":
                        route.switchToScene("MainLobbyHR");
                        break;
                    case "Payroll Manager":
                        route.switchToScene("payrollList");
                        break;
                    case "System Administrator":
                        route.switchToScene("SystemAdminHomepage");
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
    private CheckBox PasswordHide;

    @FXML
    private TextField LoginPasswordReveal;

    @FXML
    private void togglePasswordVisibility() {
        if (PasswordHide.isSelected()) {
            // Show the password in the TextField
            loginPassword.setText(LoginPasswordReveal.getText());
            LoginPasswordReveal.setVisible(false);
            loginPassword.setVisible(true);
        } else {
            // Hide the password in the PasswordField
            LoginPasswordReveal.setText(loginPassword.getText());
            LoginPasswordReveal.setVisible(true);
            loginPassword.setVisible(false);
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

    @FXML
    private TextField leaveBalanceField;

    //get id of leave text fields
    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextArea reasonTextArea;

    @FXML
    private ComboBox<Leave.LeaveType> leaveTypeComboBox;



    /*
     * TO DO:
     * Implement Attendance Checkout
     * Clear user details chache : DONE
     */
    @FXML
    private void logOut() throws Exception {
        SessionState.getInstance().setEmployee(null);
        route.switchToScene("LoginPage");
    }

    @FXML
    private void exit() throws Exception {
        System.exit(0);
    }

    private void ErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

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
        } else if (route.getCurrentSceneName().equals("EmployeeProfile") && currentEmployee != null) {
            roleLabel.setText(currentEmployee.getRole());
            departmentLabel.setText("smtg");
            positionLabel.setText(currentEmployee.getPosition());
            workExperienceLabel.setText("work 1");
            workExperienceLabel2.setText("work 2");
        } else if (route.getCurrentSceneName().equals("ApplyLeave") && currentEmployee != null) {
            leaveBalanceField.setText(String.valueOf(currentEmployee.getAnnualLeaveBalance()));
            startDatePicker.setValue(LocalDate.now());
            endDatePicker.setValue(LocalDate.now());
            // set leave types to a more prettier version
            leaveTypeComboBox.getItems().setAll(Leave.LeaveType.values());
        }
    }

    @FXML
    private void submitLeave() {
        Leave.LeaveType leaveType = leaveTypeComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String reason = reasonTextArea.getText();

        if (leaveType == null || startDate == null || endDate == null || reason.isEmpty()) {
            ErrorAlert("Please fill in all fields");
            return;
        }

        Employee currentEmployee = SessionState.getInstance().getEmployee();
        if (currentEmployee == null) {
            ErrorAlert("Error: No employee logged in");
            return;
        }

        String employeeUuid = EmployeeUtils.getEmployeeIdByUsername(EmployeeUtils.ReadData(), currentEmployee.getUsername());
        if (employeeUuid == null) {
            ErrorAlert("Error: Could not find employee UUID");
            return;
        }

        Leave leave = new Leave(employeeUuid, leaveType, startDate, endDate, reason);

        try {
            LeaveUtils.addLeaveApplication(employeeUuid, leave);
            clearLeaveFields();
            ErrorAlert("Leave application submitted successfully");
        } catch (Exception e) {
            ErrorAlert("Error submitting leave application: " + e.getMessage());
        }
    }

    private void clearLeaveFields() {
        leaveTypeComboBox.setValue(null);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        reasonTextArea.clear();
    }
}
