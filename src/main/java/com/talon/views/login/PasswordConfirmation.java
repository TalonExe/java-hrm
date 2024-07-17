package com.talon.views.login;

import java.io.IOException;

import com.talon.Router;

import javafx.fxml.FXML;

public class PasswordConfirmation {

    private final Router route;

    public PasswordConfirmation(Router route) {
        this.route = route;
    }

    @FXML
    private void switchMain() throws IOException {
        route.switchToScene("ApplyLeave");
    }
}
