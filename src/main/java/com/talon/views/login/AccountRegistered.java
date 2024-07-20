package com.talon.views.login;

import java.io.IOException;

import com.talon.Router;

import javafx.fxml.FXML;

public class AccountRegistered {

    private final Router route = Router.getInstance();

    @FXML
    private void backToLogin() throws IOException {
        route.switchToScene("LoginPage");
    }
}
