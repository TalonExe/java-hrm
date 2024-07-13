package com.talon.views.login;

import java.io.IOException;

import com.talon.App;

import javafx.fxml.FXML;

public class RegisterPage {
    @FXML
    private void switchMain() throws IOException {
        App.setRoot("ApplyLeave");
    }

}
