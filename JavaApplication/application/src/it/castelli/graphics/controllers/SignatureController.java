package it.castelli.graphics.controllers;


import it.castelli.Extensions;
import it.castelli.Paths;
import it.castelli.graphics.AlertUtil;
import it.castelli.graphics.MainApplication;
import it.castelli.graphics.Scenes;
import it.castelli.lyan.*;
import it.castelli.lyan.Certifier.Certificate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SignatureController implements Initializable {

    private Map<String, PublicUser> usersByName;
    private File selectedFile;
    private File certificateFile;
    @FXML
    private Group chooseFileGroup = new Group();
    @FXML
    private Group importCertificateGroup = new Group();
    @FXML
    private CheckBox encrypted;
    @FXML
    private Button signFile;
    @FXML
    private ListView userListView;
    @FXML
    private Label username = new Label("");

    //Run
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setText(MainApplication.currentUser.getUserName());
        chooseFileGroup.setOnMouseClicked(event -> {

            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(MainApplication.secondaryStage);
        });

        importCertificateGroup.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(Paths.CERTIFICATES_PATH));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("certificate File", "*" + Extensions.CERTIFICATES_EXTENSION)
            );
            try {
                certificateFile = fileChooser.showOpenDialog(MainApplication.secondaryStage);
            } catch (Exception e) {
                AlertUtil.showErrorAlert("Error", "An error occurred", e.getMessage());
            }
        });
    }

    public void back(ActionEvent actionEvent) {
        MainApplication.sceneWrapper(Scenes.MENU);
    }
}
