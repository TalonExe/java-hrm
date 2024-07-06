package com.talon;

import java.io.IOException;

import com.talon.models.DepartmentManager;

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
            scene = new Scene(loadFXML("LoginPage"), 640, 480);
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
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        // DepartmentManager(String, String, String, String, String, String, Date,
        // String, String, String, String, String, Department, Schedule, Payroll)
        DepartmentManager employee1 = new DepartmentManager("dfgdg", "dfgeg", "adfdf", "s fsf", "sfsldfsj", "fsuifhshf",
                "fgsd", "dsdvsdv", "sefddddd", "sefsfs", "wwwrtwrer");

        System.out.println(employee1);
        launch();

    }

}