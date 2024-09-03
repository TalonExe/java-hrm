package com.talon.controllers.hr;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import com.talon.controllers.BaseController;

public class HRMainController extends BaseController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    protected void initialize() {
        if (rootPane != null) {
            rootPane.sceneProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    rootPane.prefWidthProperty().bind(newValue.widthProperty());
                    rootPane.prefHeightProperty().bind(newValue.heightProperty());
                }
            });
        } else {
            System.err.println("rootPane is null in HRMainController initialize()");
        }
    }
}
