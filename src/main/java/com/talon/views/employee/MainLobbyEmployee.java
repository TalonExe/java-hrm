package com.talon.views;

import java.io.IOException;

import com.talon.App;

import javafx.fxml.FXML;

public class MainLobbyEmployee {
    @FXML
        private void switchApplyLeave() throws IOException {
        App.setRoot("ApplyLeave");
    }

    @FXML
        private void switchPayroll() throws IOException {
        App.setRoot("ApplyLeave");
    }

    @FXML
        private void switchProfile() throws IOException {
        App.setRoot("ApplyLeave");
    }
}