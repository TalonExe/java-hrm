package com.talon.views;
import java.io.IOException;

import com.talon.App;

import javafx.fxml.FXML;

public class ResetPassword {
    @FXML
        private void switchMain() throws IOException {
            App.setRoot("ApplyLeave");
    }
}