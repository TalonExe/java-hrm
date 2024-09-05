package com.talon;

import javafx.application.Application;
import javafx.stage.Stage;
import com.talon.utils.Router;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Router router = Router.getInstance();
        router.setPrimaryStage(stage);
        
        // Load the login scene
        router.loadScene("login", "/views/login/LoginPage.fxml");

        // Load scenes for Normal Employee
        router.loadScene("applyLeaveEmployee", "/views/normalEmployee/ApplyLeavePage.fxml");
        router.loadScene("passwordChangeEmployee", "/views/normalEmployee/PasswordChangePage.fxml");
        router.loadScene("personalProfileEmployee", "/views/normalEmployee/PersonalProfilePage.fxml");
        router.loadScene("feedbackPageEmployee", "/views/normalEmployee/FeedbackPage.fxml");
        
        // Load scenes for System Admin
        router.loadScene("personalProfileAdmin", "/views/systemAdmin/PersonalProfilePage.fxml");
        router.loadScene("applyLeaveAdmin", "/views/systemAdmin/ApplyLeavePage.fxml");
        router.loadScene("adminHomepage", "/views/systemAdmin/AdminHomepage.fxml");
        router.loadScene("manageUserAccounts", "/views/systemAdmin/ManageUserAccountsPage.fxml");
        router.loadScene("viewFeedbacks", "/views/systemAdmin/ViewFeedbacksPage.fxml");

        // Load scenes for Department Manager
        router.loadScene("applyLeaveDM", "/views/departmentManager/ApplyLeavePage.fxml");
        router.loadScene("passwordChangeDM", "/views/departmentManager/PasswordChangePage.fxml");
        router.loadScene("personalProfileDM", "/views/departmentManager/PersonalProfilePage.fxml");
        router.loadScene("departmentManagerMain", "/views/departmentManager/LeaveManagementPage.fxml");
        router.loadScene("feedbackPageDM", "/views/departmentManager/FeedbackPage.fxml");

        // Load scenes for Payroll Officer
        router.loadScene("personalProfilePO", "/views/payrollOfficer/PersonalProfilePage.fxml");
        router.loadScene("applyLeavePO", "/views/payrollOfficer/ApplyLeavePage.fxml");
        router.loadScene("passwordChangePO", "/views/payrollOfficer/PasswordChangePage.fxml");
        router.loadScene("payrollOfficerMain", "/views/payrollOfficer/PayrollOfficerMainPage.fxml");
        router.loadScene("feedbackPagePO", "/views/payrollOfficer/FeedbackPage.fxml");

        // Load scenes for HR
        router.loadScene("hrMain", "/views/hr/HRMainpage.fxml");
        router.loadScene("employmentDetailsManagement", "/views/hr/ManageEmploymentDetailsPage.fxml");
        router.loadScene("viewEmploymentDetails", "/views/hr/ViewEmploymentDetailsPage.fxml");
        router.loadScene("changePasswordHR", "/views/hr/ChangePassword.fxml");
        router.loadScene("personalProfileHR", "/views/hr/PersonalProfilePage.fxml");
        router.loadScene("feedbackPageHR", "/views/hr/FeedbackPage.fxml");
        router.loadScene("applyLeaveHR", "/views/hr/ApplyLeavePage.fxml");

        stage.setTitle("Java HRM");
        stage.setWidth(1920);
        stage.setHeight(1080);
        router.switchScene("login");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
}