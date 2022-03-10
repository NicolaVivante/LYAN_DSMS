package it.castelli.graphics;

import it.castelli.Paths;
import it.castelli.lyan.Certifier;
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
    public static Stage secondaryStage=new Stage();
    public static User currentUser;
    public static Certifier.Certificate certificateUser;

    @Override
    public void start(Stage primaryStage){
        initFolders();
        sceneWrapper(Scenes.LOGIN);
    }

    private void initFolders() {
        File usersDir = new File(Paths.USERS_PATH);
        usersDir.mkdir();
        File certificatesDir = new File(Paths.CERTIFICATES_PATH);
        certificatesDir.mkdir();
        File signedFilesDir = new File(Paths.SIGNED_FILES_PATH);
        signedFilesDir.mkdir();
        File extractedFilesDir = new File(Paths.EXTRACTED_FILES_PATH);
        extractedFilesDir.mkdir();

    }

    public static void sceneWrapper(Scenes scene) {
        try {
            URL url = new File(scene.getPath()).toURI().toURL();
            Parent root = FXMLLoader.load(url);
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.setTitle("LYAN_DSMS");
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
