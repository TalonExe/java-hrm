package com.talon.views.hr;

import java.io.IOException;

import com.talon.App;

import javafx.fxml.FXML;

public class MainLobbyHR {
    @FXML
    private void switchMain() throws IOException {
        App.setRoot("ApplyLeave");
    }
}
