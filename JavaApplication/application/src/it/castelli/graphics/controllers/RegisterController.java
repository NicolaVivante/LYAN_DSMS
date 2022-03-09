package it.castelli.graphics.controllers;

import it.castelli.graphics.AlertUtil;
import it.castelli.graphics.Scenes;
import it.castelli.graphics.MainApplication;
import it.castelli.lyan.ServerMiddleware;
import it.castelli.lyan.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private TextField username = new TextField();
    @FXML
    private TextField confirmPasswordTextField = new TextField();
    @FXML
    private TextField passwordTextField = new TextField();

    @FXML
    public void register(ActionEvent event) {
        try {
            check(passwordTextField.getText(), confirmPasswordTextField.getText(), username.getText());
        }
        catch (Exception e) {
            AlertUtil.showErrorAlert("Error", "An error occurred", e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void check(String password, String password1, String username) throws Exception {

        if (ServerMiddleware.userExists(username)) {
            throw new Exception("Username is already used, please change it ");
        } else if (password.equals(password1)) {
            User user = User.createUser(username, password);
            ServerMiddleware.registerUser(user);
            user.save();
            AlertUtil.showInformationAlert("Success", "User created", "User created successfully");
            back();
        } else {
            throw new Exception("Password confirmation differs from original password");
        }
    }

    @FXML
    public void back() {
        MainApplication.secondaryStage.close();
        MainApplication.sceneWrapper(Scenes.LOGIN);
    }
}
