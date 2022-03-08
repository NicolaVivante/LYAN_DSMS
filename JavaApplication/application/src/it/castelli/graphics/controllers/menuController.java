package it.castelli.graphics.controllers;


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

public class menuController implements Initializable {

    private static String PATH = "JavaApplication/src/main/resources/test/";
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
                AlertUtil.showInformationAlert("Signature validation", "Valid signature", "File signed by: " + signedFile.verifySignature());
                //TODO:button for download file
                signedFile.saveSourceFile(PATH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        signatureFileButton.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(PrimaryStage.secondStage);
            System.out.println(selectedFile.getName());
            Signature temp = new Signature();
            try {
                temp.start(PrimaryStage.primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
