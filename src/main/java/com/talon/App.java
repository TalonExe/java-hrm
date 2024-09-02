package com.talon;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application{
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Router route = new Router(primaryStage);

        //login
        route.addScenes("LoginPage", "/views/login/LoginPage.fxml");

        //Employee Scenes
        route.addScenes("EmployeeLeave", "/views/employee/EmployeeLeave.fxml");
        route.addScenes("ApplyLeave", "/views/employee/ApplyLeave.fxml");
        route.addScenes("EmployeeProfile", "/views/employee/EmployeeProfile.fxml");
        route.addScenes("EmployeePersonal", "/views/employee/EmployeePersonal.fxml");
        route.addScenes("EmployeeSalary", "/views/employee/EmployeeSalary.fxml");

        // //HR Scenes
        // route.addScenes("AttendanceMonthlyYearly", "/views/hr/AttendanceMonthlyYearly.fxml");
        // route.addScenes("AttendanceYearly", "/views/hr/AttendanceYearly.fxml");
        // route.addScenes("MainLobbyHR", "/views/hr/MainLobbyHR.fxml");
        // route.addScenes("MonthlyAttendance", "/views/hr/MonthlyAttendance.fxml");

        // System Administrator
        route.addScenes("SystemAdminHomepage", "/views/SystemAdmin/Homepage.fxml");
        route.addScenes("AddUser", "/views/SystemAdmin/AddUser.fxml");

        //Redirect to LoginPage upon starting the application
        route.switchToScene("LoginPage");

        primaryStage.setTitle("La Fimosa");
        primaryStage.setScene(route.getCurrentScene());
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException{
        launch();
    }
}