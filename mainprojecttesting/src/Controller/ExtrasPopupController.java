package Controller;

import Model.ExtrasData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ExtrasPopupController {

    @FXML TableView allExtras;
    @FXML TableView purchaseExtras;

    private ExtrasData extrasData = new ExtrasData();

    @FXML
    public void initialize() {
        loadAllExtras();
    }

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

    }

    private void loadAllExtras() {
        extrasData.loadList();
        allExtras.setItems(extrasData.getExtrasList());
    }

}
