package com.talon.controllers.payrollOfficer;

import com.talon.controllers.common.ApplyLeavePageController;
import com.talon.controllers.common.PersonalProfilePageController;
import javafx.fxml.FXML;

public class SideNavigationController extends PayrollOfficerMainController {
    @FXML
    private void switchToPayrollOfficerMain() {
        router.switchScene("payrollOfficerMain");
    }

    @FXML
    private void switchToPersonalProfilePO() {
        router.switchScene("personalProfilePO");
        PersonalProfilePageController controller = (PersonalProfilePageController) router.getController("personalProfilePO");
        if (controller != null) {
            controller.loadUserData();
        }
    }

    @FXML
    private void switchToApplyLeavePO() {
        router.switchScene("applyLeavePO");
        ApplyLeavePageController controller = (ApplyLeavePageController) router.getController("applyLeavePO");
        if (controller != null) {
            controller.loadEmployeeData();
        }
    }

    @FXML
    private void switchToPasswordChange() {
        router.switchScene("passwordChangePO");
    }
}
