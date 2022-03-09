package it.castelli.graphics.controllers;


import it.castelli.graphics.PrimaryStage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class signatureController implements Initializable {

    @FXML
    private Group createCertificateGroup=new Group();
    @FXML
    private Group importCertificateGroup=new Group();

    //Run
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createCertificateGroup.setOnMouseClicked(event -> {

        });
        importCertificateGroup.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("certificate File", "*.cert.lyan")
            );
            File selectedFile = fileChooser.showOpenDialog(PrimaryStage.secondStage);
        });
    }
}
