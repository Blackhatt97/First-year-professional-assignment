package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.ArrayList;


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
    @FXML TextField usernameField;

    @FXML
    public void logoutAction(ActionEvent actionEvent) {
        AnchorPane mainAnchor;
        try {
            mainAnchor = FXMLLoader.load(getClass().getResource("/View/LoginView.fxml"));
            mainViewAnchor.getChildren().setAll(mainAnchor);

        } catch (IOException ex) {
            ex.printStackTrace();
            showError();
        }
    }

    @FXML
    public void mhScreen(ActionEvent actionEvent) {
        updateWorkScreen("/View/MhView.fxml");
    }

    @FXML
    public void userScreen(ActionEvent actionEvent) {
        updateWorkScreen("/View/UserView.fxml");
    }

    @FXML
    public void customerScreen(ActionEvent actionEvent) {
        updateWorkScreen("/View/CustomerView.fxml");
    }

    @FXML
    public void rentalScreen(ActionEvent actionEvent) {
        updateWorkScreen("/View/RentalView.fxml");
    }

    @FXML
    public void extrasScreen(ActionEvent actionEvent) {
        updateWorkScreen("/View/ExtrasView.fxml");
    }

    @FXML
    public void reservScreen(ActionEvent actionEvent) {
        updateWorkScreen("/View/ReservationView.fxml");
    }

    @FXML
    public void repairScreen(ActionEvent actionEvent) {
        updateWorkScreen("/View/RepairView.fxml");
    }

    private void updateWorkScreen(String path) {
        AnchorPane wpAnchor;
        try {
            wpAnchor = FXMLLoader.load(getClass().getResource(path));
            workPlaceAnchor.getChildren().setAll(wpAnchor);
        } catch (IOException ex) {
            ex.printStackTrace();
            showError();
        }
    }

    private void showError() {
        // to do : popup error could not login coz of fxml not found or sth else
        // do it here else it is redundant ^^
    }

    void setUsernameField(String username) {
        usernameField.setText(username);
    }

    ArrayList<Button> getButtonsStaff() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(mhButton);
        buttons.add(repairButton);
        buttons.add(userButton);
        return buttons;
    }

    ArrayList<Button> getButtonsMaintenance() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(mhButton);
        buttons.add(userButton);
        buttons.add(customerButton);
        buttons.add(rentalButton);
        buttons.add(extrasButton);
        buttons.add(reservButton);
        return buttons;
    }

    TilePane getMenuPane() {
        return menuPane;
    }
}
