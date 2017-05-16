package Controller;

import Model.DBWrapper.LoginWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
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
        String userName = userField.getText();
        String password = passField.getText();
        LoginWrapper loginWrapper = new LoginWrapper();
        if(loginWrapper.loginAuthentication(userName,password) != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/MainView.fxml"));
                AnchorPane mainAnchor = fxmlLoader.load();
                ((MainController)fxmlLoader.getController()).setUsernameField(userName);
                loginAnchor.getChildren().setAll(mainAnchor);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}