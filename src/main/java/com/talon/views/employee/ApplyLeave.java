package com.talon.views.employee;

import java.io.IOException;

import com.talon.App;

import javafx.fxml.FXML;

public class ApplyLeave {
    @FXML
    private void backToApplyPage() throws IOException {
        App.setRoot("MainLobbyEmployee");
    }
}
