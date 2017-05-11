package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML Button loginButton;
    @FXML TextField userField;
    @FXML PasswordField passField;

    @FXML
    private void login(ActionEvent event) {
        // connect to the db and check password and then spawn the new stage MainView or an error message

        try {
            mainStage().show();
        } catch (IOException ex) {
            ex.printStackTrace(); // or show later some kind of error
        }

    }

    private Stage mainStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("View/MainView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Nordic Motorhomes");
        stage.setScene(scene);
        return stage;
    }

}
