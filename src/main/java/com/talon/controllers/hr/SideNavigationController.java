package com.talon.controllers.hr;

import com.talon.controllers.common.ApplyLeavePageController;
import com.talon.controllers.common.PersonalProfilePageController;
import javafx.fxml.FXML;

public class SideNavigationController extends HRMainController {
    @FXML
    private void switchToHome() {
        router.switchScene("hrMain");
        MainpageController controller = (MainpageController) router.getController("hrMain");
        if (controller != null) {
            controller.loadTableData();
        }
    }

    @FXML
    private void switchToEmploymentDetailsManagement() {
        router.switchScene("employmentDetailsManagement");
        ManageEmploymentDetailsPageController controller = (ManageEmploymentDetailsPageController) router.getController("employmentDetailsManagement");
        if (controller != null) {
            controller.setupTable();
        }
    }

    @FXML
    private void switchToChangePassword() {
        router.switchScene("changePasswordHR");
    }

    @FXML
    private void switchToPersonalProfile() {
        router.switchScene("personalProfileHR");
        PersonalProfilePageController controller = (PersonalProfilePageController) router.getController("personalProfileHR");
        if (controller != null) {
            controller.loadUserData();
        }
    }

    @FXML
    private void switchToApplyLeaveHR() {
        router.switchScene("applyLeaveHR");
        ApplyLeavePageController controller = (ApplyLeavePageController) router.getController("applyLeaveHR");
        if (controller != null) {
            controller.loadEmployeeData();
        }
    }
}
