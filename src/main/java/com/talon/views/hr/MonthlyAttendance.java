package com.talon.views.hr;
import java.io.IOException;

import com.talon.Router;

import javafx.fxml.FXML;
public class MonthlyAttendance {
     private final Router route = Router.getInstance();

    @FXML
    private void backToApplyPage() throws IOException {
        route.switchToScene("MainLobbyEmployee");
    }
}
