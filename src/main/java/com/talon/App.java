package com.talon;

import java.io.IOException;

import com.talon.controllers.EmployeeController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            scene = new Scene(loadFXML("MainLobbyEmployee"), 700, 400);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void setRoot(String fxml) throws IOException {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            System.err.println(e + "bye");
        }

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/employee/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws IOException{
        EmployeeController emp = new EmployeeController();
        launch();

    }

}