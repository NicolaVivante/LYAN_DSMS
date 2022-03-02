package it.castelli.graphics.controllers;


import it.castelli.graphics.PrimaryStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class menuController implements Initializable {


    @FXML
    private Group verifyFileButton=new Group();
    @FXML
    private Group signatureFileButton=new Group();


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
            System.out.println(selectedFile.getName());
        });
        signatureFileButton.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(PrimaryStage.secondStage);
            System.out.println(selectedFile.getName());
        });

    }

}
