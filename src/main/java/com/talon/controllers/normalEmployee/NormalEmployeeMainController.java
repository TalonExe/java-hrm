package com.talon.controllers.normalEmployee;

import com.talon.controllers.common.ApplyLeavePageController;
import com.talon.controllers.BaseController;
import javafx.fxml.FXML;

public class NormalEmployeeMainController extends BaseController {

    @FXML
    private void switchToProfile() {
        router.switchScene("personalProfileEmployee");
    }

    @FXML
    private void switchToApplyLeave() {
        router.switchScene("applyLeaveEmployee");
        ApplyLeavePageController controller = (ApplyLeavePageController) router.getController("applyLeaveEmployee");
        if (controller != null) {
            controller.loadEmployeeData();
        }
    }

    @FXML
    private void switchToPasswordChange() {
        router.switchScene("passwordChangeEmployee");
    }
}
