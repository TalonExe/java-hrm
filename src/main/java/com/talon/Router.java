package com.talon;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.talon.models.UpdatableController;

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
    private final Map<String, Object> controllers = new HashMap<>();
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
        Parent root = loader.load();
        scenes.put(name, new Scene(root));
        controllers.put(name, loader.getController());
    }

    public void switchToScene(String name) {
        if (scenes.containsKey(name)) {
            primaryStage.setScene(scenes.get(name));
            currentSceneName = name;
            Object controller = controllers.get(name);
            //checks if controller is updatable
            if (controller instanceof UpdatableController) {
                ((UpdatableController) controller).updateUI();
            }
        } else {
            System.out.println("Scene " + name + " not found!");
        }
    }

    public Scene getCurrentScene(){
        if (currentSceneName != null) {
            return scenes.get(currentSceneName);
        }else{
            return null;
        }
    }

    public Object getCurrentController() {
        return controllers.get(currentSceneName);
    }

    public String getCurrentSceneName() {
        return currentSceneName;
    }
}
