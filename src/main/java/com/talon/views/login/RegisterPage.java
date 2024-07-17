package com.talon.views.login;

import java.io.IOException;

import com.talon.Router;

import javafx.fxml.FXML;

public class RegisterPage {

    private final Router route;

    public RegisterPage(Router route) {
        this.route = route;
    }

    @FXML
    private void switchMain() throws IOException {
        route.switchToScene("ApplyLeave");
    }

}
