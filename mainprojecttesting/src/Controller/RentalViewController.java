package Controller;

import Model.DBWrapper.DBConn;
import Model.Extras;
import Model.Rental;
import Model.RentalData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RentalViewController {

    @FXML TextField idField;
    @FXML TextField custIdField;
    @FXML TextField pickupField;
    @FXML TextField dropoffField;
    @FXML TextField priceField;
    @FXML TextField extraKmField;
    @FXML TextField mhIdField;
    @FXML TextField seasonField;
    @FXML DatePicker startDate;
    @FXML DatePicker endDate;
    @FXML DatePicker rentalDate;
    @FXML ComboBox<Extras> extrasBox;
    @FXML RadioButton emptyTank;
    @FXML TableView<Rental> rentalTable;

    RentalData rentalData = new RentalData();

    @FXML
    public void initialize() {
        loadAllRentals();

        rentalTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Rental>() {
            @Override
            public void changed(ObservableValue<? extends Rental> observable, Rental oldValue, Rental newValue) {
                if (rentalTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = rentalTable.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                    updateFields((Rental) selectedItem);
                }
            }
        });
    }

    private void updateFields(Rental rental) {
        idField.setText(Integer.toString(rental.getId()));
        custIdField.setText(Integer.toString(rental.getCustId()));
        pickupField.setText(Integer.toString(rental.getPickup()));
        dropoffField.setText(Integer.toString(rental.getDropoff()));
        mhIdField.setText(Integer.toString(rental.getMotorhomeId()));
        seasonField.setText(rental.getSeason());
        startDate.setValue(rental.getStartDate().toLocalDate());
        endDate.setValue(rental.getEndDate().toLocalDate());
        rentalDate.setValue(rental.getCurrentDate().toLocalDate());
    }

    @FXML
    public void addExtras(ActionEvent e) {
        if (rentalTable.getSelectionModel().getSelectedItem() != null && !idField.getText().isEmpty()) {
            try {
                FXMLLoader root = new FXMLLoader(getClass().getResource("/View/ExtrasPopupView.fxml"));
                Scene scene = new Scene(root.load());
                ((ExtrasPopupController) root.getController()).setRentalId(Integer.parseInt(idField.getText()));
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException ex) {

            }
        }
    }

    @FXML
    public void loadExtras(ActionEvent e) {
        Rental rental = rentalTable.getSelectionModel().getSelectedItem();
        if (rental != null && !idField.getText().isEmpty()) {
            DBConn dbConn = new DBConn();
            extrasBox.setItems(dbConn.getRentalExtras(rental.getId()));
        }
    }

    private void loadAllRentals() {
        rentalData.loadList();
        rentalTable.setItems(rentalData.getRentalList());
    }

    @FXML
    public void loadRentals(ActionEvent e) {
        loadAllRentals();
    }

    @FXML
    public void resetFields(ActionEvent e) {
        rentalTable.getSelectionModel().select(null);
        idField.setText("");
        custIdField.setText("");
        pickupField.setText("");
        dropoffField.setText("");
        mhIdField.setText("");
        seasonField.setText("");
        startDate.setValue(null);
        endDate.setValue(null);
        rentalDate.setValue(null);
        priceField.setText("");
        extraKmField.setText("");
        emptyTank.setSelected(false);
        extrasBox.setItems(null);
    }

    @FXML
    public void calculatePrice(ActionEvent e) {
        // to do
    }

    @FXML
    public void createContract(ActionEvent e) {
        // to do
    }

    @FXML
    public void updateRental(ActionEvent e) {
        // to do
    }

}
