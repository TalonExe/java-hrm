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

        // Load scenes for HR
        router.loadScene("hrMain", "/views/hr/HRMainpage.fxml");
        router.loadScene("employmentDetailsManagement", "/views/hr/ManageEmploymentDetailsPage.fxml");
        router.loadScene("viewEmploymentDetails", "/views/hr/ViewEmploymentDetailsPage.fxml");
        router.loadScene("changePasswordHR", "/views/hr/ChangePassword.fxml");
        router.loadScene("personalProfileHR", "/views/hr/PersonalProfilePage.fxml");
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