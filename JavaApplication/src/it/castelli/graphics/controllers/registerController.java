package it.castelli.graphics.controllers;


import it.castelli.graphics.AlertUtil;
import it.castelli.graphics.Login;
import it.castelli.graphics.PrimaryStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class registerController implements Initializable {

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
        if(password.equals(password1))
            sendUsername(username);
        else
            AlertUtil.showErrorAlert("client error","password not valid","passwords are not equals,please check them ");

    }

    private void sendUsername(String username) throws Exception {
        //send username to the server
        boolean response=true;
        if (!response){
            AlertUtil.showErrorAlert("server error","username not valid","there's another user with the same username,try it another one");
        }
        else{
            Login temp=new Login();
            PrimaryStage.secondStage.close();
            temp.start(PrimaryStage.primaryStage);
        }
    }
}
