package it.castelli.graphics.controllers;

import it.castelli.Extensions;
import it.castelli.Paths;
import it.castelli.graphics.AlertUtil;
import it.castelli.graphics.Scenes;
import it.castelli.graphics.MainApplication;
import it.castelli.lyan.Certifier;
import it.castelli.lyan.ServerMiddleware;
import it.castelli.lyan.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CertificateController implements Initializable {

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
            fileChooser.setInitialDirectory( new File(Paths.CERTIFICATES_PATH));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("certificate File", "*"+ Extensions.CERTIFICATES_EXTENSION)
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
