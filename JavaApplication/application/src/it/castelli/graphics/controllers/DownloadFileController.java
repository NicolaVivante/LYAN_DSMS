package it.castelli.graphics.controllers;


import it.castelli.Extensions;
import it.castelli.Paths;
import it.castelli.graphics.AlertUtil;
import it.castelli.graphics.MainApplication;
import it.castelli.graphics.Scenes;
import it.castelli.lyan.SignedFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DownloadFileController implements Initializable {

    private static SignedFile signedFile;

    @FXML
    private Label textLabel = new Label();
    @FXML
    private Button downloadFileButton = new Button();
    @FXML
    private Button okButton = new Button();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Paths.SIGNED_FILES_PATH));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("user Files", "*" + Extensions.SIGNED_FILES_EXTENSION)
        );
        File selectedFile = fileChooser.showOpenDialog(MainApplication.secondaryStage);
        try {
            signedFile = SignedFile.fromFile(selectedFile, MainApplication.currentUser);
            if (signedFile.isValid()) {
                textLabel.setText("The file was signed correctly by " + signedFile.getSignatory());
            } else {
                textLabel.setText("The file was not signed correctly");

            }
        } catch (Exception e) {
            AlertUtil.showErrorAlert("Error", "An error occurred", e.getMessage());
            textLabel.setText("An error occurred");
            downloadFileButton.setDisable(true);
        }

        downloadFileButton.setOnMouseClicked(event -> {
            try {
                signedFile.saveSourceFile();
                AlertUtil.showInformationAlert("Success", "Download file", "File downloaded successfully");
                MainApplication.sceneWrapper(Scenes.MENU);
            } catch (Exception e) {
                AlertUtil.showErrorAlert("Error", "An error occurred", e.getMessage());
            }
        });

        okButton.setOnMouseClicked(event -> {
            MainApplication.sceneWrapper(Scenes.MENU);
        });

    }
}

