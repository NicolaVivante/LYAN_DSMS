package it.castelli.graphics;


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
    private TextField username=new TextField();
    @FXML
    private PasswordField passwordField=new PasswordField();
    @FXML
    private TextField passwordShowTextField=new TextField();
    @FXML
    private CheckBox showPasswordCheck=new CheckBox();
    @FXML
    private Button signUpButton=new Button();
    @FXML
    private Group chooseFileButton=new Group();

    /**
     * Controls the visibility of the Password field
     * @param event
     */
    @FXML
    public void togglevisiblePassword(ActionEvent event) {
        if (showPasswordCheck.isSelected()) {
            System.out.println("selected");
            passwordField.setText(passwordField.getText());
        }else{
            System.out.println("not selected");
        }
    }
    @FXML
    public void enterServer(ActionEvent event) {
        //enter the server
    }

    //Run
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.togglevisiblePassword(null);
        passwordShowTextField.setVisible(false);
        chooseFileButton.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("user Files", "*.lyan"),
                    new FileChooser.ExtensionFilter("text Files", "*.txt")
                    ,new FileChooser.ExtensionFilter("HTML Files", "*.htm")
            );
            File selectedFile = fileChooser.showOpenDialog(PrimaryStage.secondStage);
            System.out.println(selectedFile.getName());
        });
        signUpButton.setOnMouseClicked(event -> {
            Register temp=new Register();
            try {
                temp.start(PrimaryStage.secondStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        showPasswordCheck.selectedProperty().addListener((javafx.beans.value.ChangeListener<? super Boolean>) (observable, oldValue, newValue) -> {
            if(showPasswordCheck.isSelected()){
                passwordShowTextField.setVisible(true);
                passwordShowTextField.setText(passwordField.getText());

            }else{
                passwordShowTextField.setVisible(false);
                passwordShowTextField.setText("");
            }
        });
    }

}
