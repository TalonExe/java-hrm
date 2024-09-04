package com.talon.controllers.payrollOfficer;

import com.talon.controllers.common.ApplyLeavePageController;
import com.talon.controllers.common.PersonalProfilePageController;
import javafx.fxml.FXML;

public class SideNavigationController extends PayrollOffiverMainController {
    @FXML
    private void switchToPersonalProfileDM() {
        router.switchScene("personalProfileDM");
        PersonalProfilePageController controller = (PersonalProfilePageController) router.getController("personalProfileDM");
        if (controller != null) {
            controller.loadUserData();
        }
    }

    @FXML
    private void switchToApplyLeaveDM() {
        router.switchScene("applyLeaveDM");
        ApplyLeavePageController controller = (ApplyLeavePageController) router.getController("applyLeaveDM");
        if (controller != null) {
            controller.loadEmployeeData();
        }
    }

    @FXML
    private void switchToPasswordChange() {
        router.switchScene("passwordChangeDM");
    }
}
