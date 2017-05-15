package Controller;

import Model.DBWrapper.LoginWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
        AnchorPane mainAnchor;
        LoginWrapper loginWrapper = new LoginWrapper();
        if(loginWrapper.loginAuthentication(userName,password) != null) {
            try {
                mainAnchor = FXMLLoader.load(getClass().getResource("/View/MainView.fxml"));
                loginAnchor.getChildren().setAll(mainAnchor);
                for(Node node: mainAnchor.getChildren()) {
                    if(node.getId() != null && node.getId().equals("rightSplit")) {
                        for(Node rightNode : ((SplitPane) node).getItems()) {
                            if(rightNode.getId() != null && rightNode.getId().equals("rightPane")) {
                                for(Node nextNode : ((AnchorPane) rightNode).getChildren()) {
                                    if(nextNode.getId() != null && nextNode.getId().equals("usernameField")) {
                                        ((TextField) nextNode).setText(userName);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}