package it.castelli.graphics.applications;

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
        new LoginApplication().start(primaryStage);
    }
    public static void sceneWrapper(String path){
        try {
            URL url = new File(path).toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
