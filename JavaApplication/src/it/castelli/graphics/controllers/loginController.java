package it.castelli.graphics.controllers;


import it.castelli.graphics.AlertUtil;
import it.castelli.graphics.Menu;
import it.castelli.graphics.PrimaryStage;
import it.castelli.graphics.Register;
import it.castelli.lyan.SourceFile;
import it.castelli.lyan.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    @FXML
    private TextField passwordTextField = new TextField();
    @FXML
    private Button signUpButton = new Button();
    @FXML
    private Button enterServer = new Button();
    @FXML
    private Group chooseFileButton = new Group();




    //Run
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseFileButton.setOnMouseClicked(event -> {
            if(passwordTextField.getText().length()==0)
                AlertUtil.showErrorAlert("error","password error","insert a password before choose a file");
            else {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("user Files", "*.user.lyan")
                );
                File selectedFile = fileChooser.showOpenDialog(PrimaryStage.secondStage);
                try {
                    User user = User.fromFile(selectedFile, passwordTextField.getText());
                } catch (Exception e) {
                    AlertUtil.showErrorAlert("error", "error header", e.getMessage());
                }
                System.out.println(selectedFile.getName());
            }
        });
        signUpButton.setOnMouseClicked(event -> {
            Register temp = new Register();
            try {
                temp.start(PrimaryStage.secondStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        enterServer.setOnMouseClicked(event -> {
            it.castelli.graphics.Menu temp = new Menu();
            try {
                temp.start(PrimaryStage.primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
