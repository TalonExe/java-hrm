package com.talon.views.login;

import java.io.IOException;

import com.talon.Router;

import javafx.fxml.FXML;

public class RegisterPage {

    private final Router route = Router.getInstance();

    @FXML
    private void backToLogin() throws IOException {
        route.switchToScene("LoginPage");
    }

    @FXML
    private void registerProcess() throws IOException {
        route.switchToScene("AccountRegistered");
    }
}
