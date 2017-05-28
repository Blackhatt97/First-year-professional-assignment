package Controller;

import Model.DBWrapper.LoginWrapper;
import Model.ErrorHandler;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LoginController {

    @FXML Button loginButton;
    @FXML TextField userField;
    @FXML PasswordField passField;
    @FXML AnchorPane loginAnchor;
    @FXML Label labelId;
    int freezeCount = 0;
    int failedAttempts = 0;
    @FXML
    private void login(ActionEvent event) throws InterruptedException {
        String userName = userField.getText();
        String password = passField.getText();
        LoginWrapper loginWrapper = new LoginWrapper();
        User user = loginWrapper.loginAuthentication(userName, password);

        freezeCount++;
        if (user != null && freezeCount < 5) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/MainView.fxml"));
                AnchorPane mainAnchor = fxmlLoader.load();
                MainController mainController = fxmlLoader.getController();
                TilePane menuPane = mainController.getMenuPane();
                if (user.getType().equals("Staff")) {
                    menuPane.getChildren().removeAll(mainController.getButtonsStaff());
                }
                if (user.getType().equals("Maintenance")) {
                    menuPane.getChildren().removeAll(mainController.getButtonsMaintenance());
                }
                mainController.setUsernameField(userName);
                loginAnchor.getChildren().setAll(mainAnchor);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (user == null && freezeCount == 4) {
            freezeCount = 0;
            failedAttempts += 5;

            ErrorHandler.bruteForceSafe(labelId, loginButton, failedAttempts);
        }
    }
}