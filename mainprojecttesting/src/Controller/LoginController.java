package Controller;


import Model.DBWrapper.DBConn;
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
        Connection conn = null;
        try {
            conn = DBConn.getConn();
            String query = "SELECT * FROM `users` " +
                    "WHERE `email` = ? AND `pass` = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, userName);
            ps.setString(2, password);
            ps.execute();

            ResultSet rs = ps.getResultSet();
            if (rs.first()) {
                mainAnchor = FXMLLoader.load(getClass().getResource("/View/MainView.fxml"));
                loginAnchor.getChildren().setAll(mainAnchor);
            } else
                popUp("Failed", "Invalid username and/or password", "Close");

        } catch (Exception ex) {
            ex.printStackTrace(); // to do : popup error could not login coz of fxml not found
        }
    }

     private void popUp(String title, String text, String button) {

         Stage popupwindow = new Stage();

         popupwindow.initModality(Modality.APPLICATION_MODAL);

         popupwindow.setTitle(title);
         Label label1 = new Label(text);
         Button button1 = new Button(button);
         button1.setOnAction(e -> popupwindow.close());

         VBox layout = new VBox(10);

         layout.getChildren().addAll(label1, button1);
         layout.setAlignment(Pos.CENTER);

         Scene scene1 = new Scene(layout, 300, 250);

         popupwindow.setScene(scene1);
         popupwindow.showAndWait();
     }

}