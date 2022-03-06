package it.castelli.graphics.controllers;


import it.castelli.graphics.AlertUtil;
import it.castelli.graphics.Menu;
import it.castelli.graphics.PrimaryStage;
import it.castelli.graphics.Register;
import it.castelli.lyan.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    public String password;

    @FXML
    private Group signUpButton = new Group();
    @FXML
    private Button enterServer = new Button();
    @FXML
    private Group chooseFileButton = new Group();


    //Run
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseFileButton.setOnMouseClicked(event -> {

                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("user Files", "*.user.lyan")
                );
                File selectedFile = fileChooser.showOpenDialog(PrimaryStage.secondStage);
                buttonReset();
                System.out.println(password);
                try {
                    User user = User.fromFile(selectedFile, password);
                } catch (Exception e) {
                    AlertUtil.showErrorAlert("error", "error header", e.getMessage());
                }
                System.out.println(selectedFile.getName());

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
            Menu temp = new Menu();
            try {
                temp.start(PrimaryStage.primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void buttonReset() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("PASSWORD");
        dialog.setHeaderText("for use this file is request a password that you must know");
        dialog.setGraphic(new Circle(15, Color.RED)); // Custom graphic
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        PasswordField pwd = new PasswordField();
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10);
        content.getChildren().addAll(new Label("insert the password for the file selected:"), pwd);
        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                password=pwd.getText();
                return pwd.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
    }
}
