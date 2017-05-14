package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;

public class MainController {

    @FXML Button mhButton;
    @FXML Button userButton;
    @FXML Button customerButton;
    @FXML Button rentalButton;
    @FXML Button extrasButton;
    @FXML Button reservButton;
    @FXML Button repairButton;
    @FXML Button logoutButton;
    @FXML TilePane menuPane;
    @FXML AnchorPane mainViewAnchor;
    @FXML AnchorPane workPlaceAnchor;

    @FXML
    public void logoutAction(ActionEvent actionEvent) {
        AnchorPane mainAnchor;
        try {
            mainAnchor = FXMLLoader.load(getClass().getResource("/View/LoginView.fxml"));
            mainViewAnchor.getChildren().setAll(mainAnchor);

        } catch (IOException ex) {
            ex.printStackTrace(); // to do : popup error could not login coz of fxml not found
        }
    }

    @FXML
    public void mhScreen(ActionEvent actionEvent) {
        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource("/View/MhView.fxml"));
            workPlaceAnchor.getChildren().setAll(wpAnchor);

        } catch (IOException ex) {
            ex.printStackTrace(); // to do : popup error could not login coz of fxml not found
        }
    }

    @FXML
    public void userScreen(ActionEvent actionEvent) {
        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource("/View/UserView.fxml"));
            workPlaceAnchor.getChildren().setAll(wpAnchor);

        } catch (IOException ex) {
            ex.printStackTrace(); // to do : popup error could not login coz of fxml not found
        }
    }

    @FXML
    public void customerScreen(ActionEvent actionEvent) {
        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource("/View/CustomerView.fxml"));
            workPlaceAnchor.getChildren().setAll(wpAnchor);

        } catch (IOException ex) {
            ex.printStackTrace(); // to do : popup error could not login coz of fxml not found
        }
    }

    @FXML
    public void rentalScreen(ActionEvent actionEvent) {
        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource("/View/RentalView.fxml"));
            workPlaceAnchor.getChildren().setAll(wpAnchor);

        } catch (IOException ex) {
            ex.printStackTrace(); // to do : popup error could not login coz of fxml not found
        }
    }

    @FXML
    public void extrasScreen(ActionEvent actionEvent) {
        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource("/View/ExtrasView.fxml"));
            workPlaceAnchor.getChildren().setAll(wpAnchor);

        } catch (IOException ex) {
            ex.printStackTrace(); // to do : popup error could not login coz of fxml not found
        }
    }

    @FXML
    public void reservScreen(ActionEvent actionEvent) {
        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource("/View/ReservationView.fxml"));
            workPlaceAnchor.getChildren().setAll(wpAnchor);

        } catch (IOException ex) {
            ex.printStackTrace(); // to do : popup error could not login coz of fxml not found
        }
    }

    @FXML
    public void repairScreen(ActionEvent actionEvent) {
        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource("/View/RepairView.fxml"));
            workPlaceAnchor.getChildren().setAll(wpAnchor);

        } catch (IOException ex) {
            ex.printStackTrace(); // to do : popup error could not login coz of fxml not found
        }
    }
}
