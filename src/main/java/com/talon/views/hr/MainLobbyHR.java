package com.talon.views.hr;

import java.io.IOException;

import com.talon.Router;

import javafx.fxml.FXML;

public class MainLobbyHR {

    private final Router route = Router.getInstance();

    @FXML
    private void switchMain() throws IOException {
        route.switchToScene("ApplyLeave");
    }
}
