package com.talon.controllers.departmentManager;

import com.talon.controllers.common.ApplyLeavePageController;
import com.talon.controllers.common.PersonalProfilePageController;
import javafx.fxml.FXML;

public class SideNavigationController extends DepartmentManagerMainController {
    @FXML
    private void switchToHome() {
        router.switchScene("departmentManagerMain");
        LeaveManagementPageController controller = (LeaveManagementPageController) router.getController("departmentManagerMain");
        if (controller != null) {
            controller.refreshLeaveApplications();
        }
    }

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

    @FXML
    private void switchToFeedbackPageDM() {
        router.switchScene("feedbackPageDM");
    }
}
