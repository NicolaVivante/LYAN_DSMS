package it.castelli.graphics.controllers;


import it.castelli.Paths;
import it.castelli.graphics.AlertUtil;
import it.castelli.graphics.PrimaryStage;
import it.castelli.graphics.Signature;
import it.castelli.lyan.SignedFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Group verifyFileButton = new Group();
    @FXML
    private Group signatureFileButton = new Group();


    @FXML
    public void enterServer(ActionEvent event) {
        //enter the server
    }

    //Run
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        verifyFileButton.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("user Files", "*.sig.lyan")
            );
            File selectedFile = fileChooser.showOpenDialog(PrimaryStage.secondStage);
            try {
                SignedFile signedFile = SignedFile.fromFile(selectedFile, PrimaryStage.currentUser);
                AlertUtil.showInformationAlert("Signature validation", "Valid signature", "File signed by: " + signedFile.getSignatory());
                //TODO: button to download file
                signedFile.saveSourceFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        signatureFileButton.setOnMouseClicked(event -> {
            Signature temp = new Signature();
            try {
                temp.start(PrimaryStage.primaryStage);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
