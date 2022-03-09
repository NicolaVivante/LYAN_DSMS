package it.castelli.graphics.controllers;


import it.castelli.graphics.AlertUtil;
import it.castelli.graphics.MainApplication;
import it.castelli.graphics.Scenes;
import it.castelli.lyan.Certifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {


    @FXML
    private Group createCertificateGroup = new Group();
    @FXML
    private Group verifyFileButton = new Group();
    @FXML
    private Group signatureFileButton = new Group();
    @FXML
    private Label username = new Label("");

    //Run
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setText(MainApplication.currentUser.getUserName());
        createCertificateGroup.setOnMouseClicked(event -> {
            try {
                Certifier.createCertificate(MainApplication.currentUser).save();
                AlertUtil.showInformationAlert("Certificate", "Certificate created", "Certificate created successfully");
            } catch (Exception e) {
                AlertUtil.showErrorAlert("Error", "An error occurred", e.getMessage());
            }
        });

        verifyFileButton.setOnMouseClicked(event -> {
            MainApplication.sceneWrapper(Scenes.DOWNLOAD_FILE);
        });
        signatureFileButton.setOnMouseClicked(event -> {
            MainApplication.sceneWrapper(Scenes.SIGNATURE);
        });

    }

    public void back(ActionEvent actionEvent) {
        MainApplication.sceneWrapper(Scenes.LOGIN);
    }
}
