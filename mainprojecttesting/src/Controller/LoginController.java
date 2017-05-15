package Controller;

import Model.DBWrapper.DBConn;
import Model.DBWrapper.LoginWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML Button loginButton;
    @FXML TextField userField;
    @FXML PasswordField passField;
    @FXML AnchorPane loginAnchor;

    @FXML
    private void login(ActionEvent event) {
        // connect to the db and check password and then spawn the new stage MainView or an error message
        // then delete the text from user and pass fields
        String userName = userField.getText();
        String password = passField.getText();
        AnchorPane mainAnchor;
        LoginWrapper loginWrapper = new LoginWrapper();
        if(loginWrapper.loginAuthentication(userName,password) != null)
        {

            try{
                mainAnchor = FXMLLoader.load(getClass().getResource("/View/MainView.fxml"));
                loginAnchor.getChildren().setAll(mainAnchor);

                MainController mainController = new MainController();
                mainController.userNameLabel();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

    }
}