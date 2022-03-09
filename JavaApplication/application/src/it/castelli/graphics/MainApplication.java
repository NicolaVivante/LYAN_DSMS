package it.castelli.graphics;

import it.castelli.graphics.Scenes;
import it.castelli.lyan.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class MainApplication extends Application {
    public static Stage primaryStage=new Stage();
    public static Stage secondaryStage =new Stage();
    public static User currentUser;


    @Override
    public void start(Stage primaryStage){
        sceneWrapper(Scenes.LOGIN, MainApplication.primaryStage);
    }

    public static void sceneWrapper(Scenes scene, Stage stage){
        try {
            URL url = new File(scene.getPath()).toURI().toURL();
            Parent root = FXMLLoader.load(url);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("LYAN_DSMS");
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
