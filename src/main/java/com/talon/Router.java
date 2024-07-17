package com.talon;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Router{

    //move set root function here
    //add update function everytime a new page is clicked
    //pubsub???
    private final Stage primaryStage;
    private final Map<String, Scene> scenes = new HashMap<>();
    private String currentSceneName;
    private static Router route;
    
    public Router(Stage primaryStage){
        this.primaryStage = primaryStage;
        route = this;
    }

    public static synchronized Router getInstance() {
        return route;
    }

    public void addScenes(String name, String fxmlFile) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        System.out.println(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        System.out.println(root);
        scenes.put(name, new Scene(root));
        System.out.println(scenes);
        
    }

    public void switchToScene(String name) {
        if (scenes.containsKey(name)) {
            primaryStage.setScene(scenes.get(name));
            currentSceneName = name;
        } else {
            System.out.println("Scene " + name + " not found!");
        }
    }

    public Scene getCurrentScene(){
        if (currentSceneName != null) {
            System.out.println("hi");
            return scenes.get(currentSceneName);
        }else{
            return null;
        }
    }
}
