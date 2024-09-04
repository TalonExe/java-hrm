package com.talon.controllers.systemAdmin;

import com.talon.controllers.common.ApplyLeavePageController;
import com.talon.controllers.common.PersonalProfilePageController;

import javafx.fxml.FXML;

public class SideNavigationController extends SystemAdminMainController {
    @FXML
    private void switchToHome() {
        router.switchScene("adminHomepage");
        HomepageController controller = (HomepageController) router.getController("adminHomepage");
        if (controller != null) {
            controller.refreshHomepage();
        }
    }

    @FXML
    private void switchToPersonalProfile() {
        router.switchScene("personalProfileAdmin");
        PersonalProfilePageController controller = (PersonalProfilePageController) router.getController("personalProfileAdmin");
        if (controller != null) {
            controller.loadUserData();
        }
    }

    @FXML
    private void switchToApplyLeaveAdmin() {
        router.switchScene("applyLeaveAdmin");
        ApplyLeavePageController controller = (ApplyLeavePageController) router.getController("applyLeaveAdmin");
        if (controller != null) {
            controller.loadEmployeeData();
        }
    }

    @FXML
    private void switchToManageUserAccounts() {
        router.switchScene("manageUserAccounts");
        ManageUserAccountsPageController controller = (ManageUserAccountsPageController) router.getController("manageUserAccounts");
        if (controller != null) {
            controller.refreshManageUserData();
        }
    }
}
