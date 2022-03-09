package it.castelli.graphics.controllers;


import it.castelli.graphics.AlertUtil;
import it.castelli.graphics.Scenes;
import it.castelli.graphics.MainApplication;
import it.castelli.lyan.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public String password;

    @FXML
    private Group newUserButton = new Group();
    @FXML
    private Button enterServer = new Button();
    @FXML
    private Group chooseFileButton = new Group();

    //Run
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseFileButton.setOnMouseClicked(event -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("user Files", "*.user.lyan")
            );
            File selectedFile = fileChooser.showOpenDialog(MainApplication.secondaryStage);

            boolean continuePassword = false;
            do {
                try {
                    Optional<String> result = AlertUtil.showTextInputDialogue("", "Password", "Type password",
                            "The file is not accessible if not through a password");
                    if (result.isPresent()) {
                        password = result.get();
                        MainApplication.currentUser = User.fromFile(selectedFile, password);
                        MainApplication.secondaryStage.close();
                        MainApplication.sceneWrapper(Scenes.MENU,MainApplication.primaryStage);
                    } else continuePassword = false;
                }
                catch (Exception e) {
                    AlertUtil.showErrorAlert("Error", "An error occurred", e.getMessage());
                    continuePassword = true;
                }
            } while (continuePassword);

        });
        newUserButton.setOnMouseClicked(event -> {
                MainApplication.sceneWrapper(Scenes.REGISTER,MainApplication.primaryStage);
        });
        enterServer.setOnMouseClicked(event -> {
            MainApplication.sceneWrapper(Scenes.MENU,MainApplication.primaryStage);
        });

    }
}
