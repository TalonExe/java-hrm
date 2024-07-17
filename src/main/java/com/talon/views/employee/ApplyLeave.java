package com.talon.views.employee;

import java.io.IOException;

import com.talon.App;

import javafx.fxml.FXML;

public class ApplyLeave {
    @FXML
    private void backToApplyPage() throws IOException {
        App.setRoot("MainLobbyEmployee");
    }

    @FXML
    private void switchApplyLeave() throws IOException {
        App.setRoot("ApplyLeave");
    }

    @FXML
    private void switchPayroll() throws IOException {
        App.setRoot("EmployeeSalary");
    }

    @FXML
    private void switchProfile() throws IOException {
        App.setRoot("EmployeeProfile");
    }
}
