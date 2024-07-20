package com.talon.views.login;

import java.io.IOException;

import com.talon.Router;

import javafx.fxml.FXML;

public class ResetPassword {

    private final Router route = Router.getInstance();

    @FXML
    private void resetConfirmation() throws IOException {
        route.switchToScene("PasswordChange");
    }
}