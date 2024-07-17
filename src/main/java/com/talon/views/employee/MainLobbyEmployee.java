package com.talon.views.employee;

import java.io.IOException;

import com.talon.Router;

import javafx.fxml.FXML;

public class MainLobbyEmployee {

    private final Router route = Router.getInstance();

    @FXML
    private void switchApplyLeave() throws IOException {
        route.switchToScene("ApplyLeave");
    }

    @FXML
    private void switchPayroll() throws IOException {
        route.switchToScene("EmployeeSalary");
    }

    @FXML
    private void switchProfile() throws IOException {
        route.switchToScene("EmployeeProfile");
    }
}