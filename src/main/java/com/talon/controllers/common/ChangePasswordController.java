package com.talon.controllers.common;

import com.talon.controllers.BaseController;
import com.talon.utils.EmployeeUtils;
import com.talon.utils.SessionState;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class ChangePasswordController extends BaseController {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleChangePassword() {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            ErrorAlert("All fields are required");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            ErrorAlert("New password and confirm password do not match");
            return;
        }

        try {
            String employeeId = SessionState.getInstance().getLoggedInUserId();
            if (employeeId == null) {
                ErrorAlert("User session not found");
                return;
            }

            if (EmployeeUtils.changePassword(employeeId, newPassword)) {
                SuccessAlert("Password changed successfully");
                clearFields();
            } else {
                ErrorAlert("Failed to change password. Please try again.");
            }
        } catch (Exception e) {
            ErrorAlert("An error occurred: " + e.getMessage());
        }
    }

    private void clearFields() {
        newPasswordField.clear();
        confirmPasswordField.clear();
    }
}
