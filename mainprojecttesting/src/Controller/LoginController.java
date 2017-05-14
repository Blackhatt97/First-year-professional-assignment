package Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

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
        try {
            mainAnchor = FXMLLoader.load(getClass().getResource("/View/MainView.fxml"));
            loginAnchor.getChildren().setAll(mainAnchor);

        } catch (IOException ex) {
            ex.printStackTrace(); // to do : popup error could not login coz of fxml not found
        }
    }

}