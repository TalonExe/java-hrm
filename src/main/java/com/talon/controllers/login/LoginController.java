package com.talon.controllers.login;

import com.talon.controllers.BaseController;
import com.talon.controllers.common.ApplyLeavePageController;
import com.talon.models.Employee;
import com.talon.utils.EmployeeUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import com.talon.utils.SessionState;
import com.talon.controllers.common.PersonalProfilePageController;

public class LoginController extends BaseController {
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private void loginProcess() {
        String usernameInput = loginUsername.getText();
        String passwordInput = loginPassword.getText();

        try {
            Employee loggedInEmployee = EmployeeUtils.findByUsername(usernameInput);
            if (loggedInEmployee == null || !EmployeeUtils.verifyPassword(passwordInput, loggedInEmployee.getPassword())) {
                handleFailedLogin(usernameInput);
                return;
            }

            if (isAccountInvalid(loggedInEmployee)) {
                return;
            }

            handleSuccessfulLogin(loggedInEmployee);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            ErrorAlert("An error occurred during login");
        }
    }

    private boolean isAccountInvalid(Employee employee) {
        if (employee.getAccountDisabled()) {
            ErrorAlert("Your account has been disabled");
            return true;
        }
        if (employee.getAccountStatus().equalsIgnoreCase("LOCKED")) {
            ErrorAlert("Your account has been locked");
            return true;
        }
        return false;
    }

    private void handleFailedLogin(String username) throws Exception {
        ErrorAlert("Invalid username or password");
        EmployeeUtils.updateLoginStatus(username, "FAILED");
    }

    private void handleSuccessfulLogin(Employee employee) throws Exception {
        clearLoginFields();
        EmployeeUtils.updateLoginStatus(employee.getUsername(), "SUCCESS");
        Map<String, Employee> employees = EmployeeUtils.ReadData();
        String employeeId = null;
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            if (entry.getValue().getUsername().equals(employee.getUsername())) {
                employeeId = entry.getKey();
                break;
            }
        }
        
        // Set the logged-in user ID in the SessionState
        SessionState.getInstance().setLoggedInUserId(employeeId);
        
        EmployeeUtils.createAttendanceRecord(employeeId, LocalDate.now().toString(), LocalTime.now().toString());
        switchToAppropriateScene(employee.getRole());
        
        // Load user data in the appropriate PersonalProfilePageController
        String profileSceneName = getProfileSceneName(employee.getRole());
        PersonalProfilePageController profileController = (PersonalProfilePageController) router.getController(profileSceneName);
        if (profileController != null) {
            profileController.loadUserData();
        }
    }

    private String getProfileSceneName(String role) {
        switch (role) {
            case "HR Officer":
                return "personalProfileHR";
            case "Payroll Manager":
                return "personalProfilePayroll";
            case "System Administrator":
                return "personalProfileAdmin";
            default:
                return "personalProfileEmployee";
        }
    }

    private void clearLoginFields() {
        loginUsername.setText("");
        loginPassword.setText("");
    }

    private void switchToAppropriateScene(String role) {
        String sceneName = switch (role) {
            case "HR Officer" -> "hrMain";
            case "Payroll Manager" -> "Payroll_Lobby";
            case "System Administrator" -> "adminHomepage";
            default -> "applyLeaveEmployee";
        };
        if (sceneName.equals("applyLeaveEmployee")) {
            ApplyLeavePageController controller = (ApplyLeavePageController) router.getController("applyLeaveEmployee");
            if (controller != null) {
                controller.loadEmployeeData();
            }
        }
        router.switchScene(sceneName);
    }

    @FXML
    public void initialize() {
        rootPane.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                rootPane.prefWidthProperty().bind(newValue.widthProperty());
                rootPane.prefHeightProperty().bind(newValue.heightProperty());
            }
        });
    }
}