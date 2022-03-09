package it.castelli.graphics.applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class LoginApplication extends Application {


    @Override
    public void start(Stage primaryStage){
        try {
            URL url = new File("res/fxmls/login.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            MainApplication.primaryStage.setScene(scene);
            MainApplication.primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
