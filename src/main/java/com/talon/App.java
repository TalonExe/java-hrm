package com.talon;

import java.io.IOException;

import com.talon.controllers.EmployeeController;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application{
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Router route = new Router(primaryStage);

        //Employee Scenes
        route.addScenes("MainLobbyEmployee", "views/employee/MainLobbyEmployee.fxml");
        route.addScenes("ApplyLeave", "views/employee/ApplyLeave.fxml");
        route.addScenes("EmployeeProfile", "views/employee/EmployeeProfile.fxml");
        route.addScenes("EmployeePersonal", "views/employee/EmployeePersonal.fxml");
        route.addScenes("EmployeeSalary", "views/employee/EmployeeSalary.fxml");

        route.switchToScene("MainLobbyEmployee");

        primaryStage.setTitle("La Fimosa");
        primaryStage.setScene(route.getCurrentScene());
        primaryStage.show();

    }

    public static void main(String[] args) throws IOException{

        EmployeeController empCont = new EmployeeController();
        launch();

    }

}