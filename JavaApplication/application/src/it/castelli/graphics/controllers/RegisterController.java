package it.castelli.graphics.controllers;


import it.castelli.graphics.AlertUtil;
import it.castelli.graphics.Login;
import it.castelli.graphics.PrimaryStage;
import it.castelli.lyan.ServerMiddleware;
import it.castelli.lyan.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private static final String USER_PATH = "C:\\Users\\asus\\Downloads\\";
    @FXML
    private TextField username=new TextField();
    @FXML
    private TextField confirmPasswordTextField=new TextField();
    @FXML
    private TextField passwordTextField=new TextField();



    @FXML
    public void register(ActionEvent event) throws Exception {
        //enter the server
        check(passwordTextField.getText(), confirmPasswordTextField.getText(), username.getText());
    }

    //Run
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    private void check(String password,String password1,String username) throws Exception {
//        control of the username
        if(ServerMiddleware.userExists(username)) {
            AlertUtil.showErrorAlert("client error","username not valid","username is already used, please change it ");
        }
        else
            if(password.equals(password1)) {
                User user=User.createUser(username,password);
                ServerMiddleware.registerUser(user);
                user.save(USER_PATH);
                AlertUtil.showInformationAlert("User","User created","User created successfully");
                back();
            } else
                AlertUtil.showErrorAlert("client error","password not valid","passwords are not equals,please check them ");

    }

    @FXML
    public void back() throws Exception {
        Login temp=new Login();
        PrimaryStage.secondStage.close();
        temp.start(PrimaryStage.primaryStage);
    }

}
