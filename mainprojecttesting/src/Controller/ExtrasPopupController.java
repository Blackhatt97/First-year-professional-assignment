package Controller;

import Model.DBWrapper.DBConn;
import Model.Extras;
import Model.ExtrasData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ExtrasPopupController {

    @FXML TableView<Extras> allExtras;
    @FXML TableView<Extras> purchaseExtras;
    @FXML Button doneButton;

    private ExtrasData extrasData = new ExtrasData();
    private int rentalId;

    @FXML
    public void addExtra(ActionEvent e) {
        if (allExtras.getSelectionModel().getSelectedItem() != null) {
            purchaseExtras.getItems().add(allExtras.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void removeExtra(ActionEvent e) {
        if (purchaseExtras.getSelectionModel().getSelectedItem() != null) {
            purchaseExtras.getItems().remove(purchaseExtras.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void loadExtras(ActionEvent e) {
        loadAllExtras();
    }

    @FXML
    public void sendExtras(ActionEvent e) {
        DBConn dbConn = new DBConn();
        dbConn.deleteRentalExtras(rentalId);
        dbConn.sendExtrasToDB(purchaseExtras.getItems(), rentalId);
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }

    private void loadAllExtras() {
        extrasData.loadList();
        allExtras.setItems(extrasData.getExtrasList());
        DBConn dbConn = new DBConn();
        purchaseExtras.setItems(dbConn.getRentalExtras(rentalId));
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

}
