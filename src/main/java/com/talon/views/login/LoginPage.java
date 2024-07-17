package com.talon.views.login;

import java.io.IOException;

import com.talon.Router;

import javafx.fxml.FXML;

public class LoginPage {

    private final Router route;

    public LoginPage(Router route) {
        this.route = route;
    }

    @FXML
    private void switchMain() throws IOException {
        route.switchToScene("ApplyLeave");
    }
}
