package com.talon.views.login;

import java.io.IOException;

import com.talon.Router;

import javafx.fxml.FXML;

public class LoginPage {

    private final Router route = Router.getInstance();

    @FXML
    private void switchForgot() throws IOException {
        route.switchToScene("ResetPassword");
    }

    @FXML
    private void switchRegister() throws IOException {
        route.switchToScene("RegisterPage");
    }

    @FXML
    private void loginProcess() throws IOException {
        route.switchToScene("MainLobbyEmployee");
    }
}
