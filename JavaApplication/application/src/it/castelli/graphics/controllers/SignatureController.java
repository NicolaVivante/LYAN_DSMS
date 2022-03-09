package it.castelli.graphics.controllers;


import it.castelli.graphics.AlertUtil;
import it.castelli.graphics.MainApplication;
import it.castelli.lyan.Certifier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SignatureController implements Initializable {

    @FXML
    private Group createCertificateGroup=new Group();
    @FXML
    private Group importCertificateGroup=new Group();

    //Run
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createCertificateGroup.setOnMouseClicked(event -> {
            try {
                Certifier.createCertificate(MainApplication.currentUser).save();
                AlertUtil.showInformationAlert("Certificate","Certificate created","Certificate created successfully");
            } catch (Exception e) {
                AlertUtil.showErrorAlert("Error", "An error occurred", e.getMessage());
            }
        });

        importCertificateGroup.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("certificate File", "*.cert.lyan")
            );
            File selectedFile = fileChooser.showOpenDialog(MainApplication.secondaryStage);
            try {
                Certifier.Certificate certificate=Certifier.fromFile(selectedFile);
            } catch (Exception e) {
                AlertUtil.showErrorAlert("Error", "An error occurred", e.getMessage());
            }
        });
    }
}
