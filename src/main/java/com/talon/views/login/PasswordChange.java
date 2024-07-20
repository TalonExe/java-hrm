package com.talon.views.login;

import java.io.IOException;

import com.talon.Router;

import javafx.fxml.FXML;

public class PasswordChange {

    private final Router route = Router.getInstance();

    @FXML
    private void changeConfirmation() throws IOException {
        route.switchToScene("PasswordConfirmation");
    }
}
