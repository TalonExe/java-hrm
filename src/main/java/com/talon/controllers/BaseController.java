package com.talon.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import com.talon.utils.Router;
import com.talon.utils.EmployeeUtils;
import com.talon.utils.SessionState;
import java.time.LocalDate;
import java.time.LocalTime;

public class BaseController {
    protected Router router = Router.getInstance();

    // Display Success Message
    protected void SuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Display Warning Message
    protected void WarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Display Error Message
    protected void ErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void logOut() {
        String employeeId = SessionState.getInstance().getLoggedInUserId();
        if (employeeId != null) {
            try {
                EmployeeUtils.clockOut(employeeId, LocalDate.now().toString(), LocalTime.now().toString());
            } catch (Exception e) {
                System.err.println("Error during clock out: " + e.getMessage());
            }
        }
        SessionState.getInstance().clearSession();
        router.switchScene("login");
    }
}
