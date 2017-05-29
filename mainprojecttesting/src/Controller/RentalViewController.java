package Controller;

import Model.DBWrapper.DBConn;
import Model.Extras;
import Model.PriceCalculator;
import Model.Rental;
import Model.RentalData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
        loadRentalExtras();
    }

    private void loadRentalExtras() {
        Rental rental = rentalTable.getSelectionModel().getSelectedItem();
        if (rental != null && !idField.getText().isEmpty()) {
            DBConn dbConn = new DBConn();
            ArrayList<Extras> extrasArrayList = new ArrayList<>();
            ObservableList<Extras> extrasObservableList = dbConn.getRentalExtras(rental.getId());
            extrasArrayList.addAll(extrasObservableList);
            extrasBox.setItems(extrasObservableList);
            rental.setExtra(extrasArrayList);
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
        loadRentalExtras();
        Rental rental = rentalTable.getSelectionModel().getSelectedItem();
        if (rental != null && !idField.getText().isEmpty()) {
            DBConn dbConn = new DBConn();
            PriceCalculator priceCalculator = new PriceCalculator();
            double price1 = priceCalculator.getPrice(
                    rental.getStartDate().toLocalDate(),
                    rental.getEndDate().toLocalDate(),
                    rental.getSeason(),
                    dbConn.getMotorhomeType(rental.getMotorhomeId())
            );
            double price2 = priceCalculator.getExtrasPrice(rental.getExtra());
            double extraKmPrice = Double.parseDouble(extraKmField.getText()) * 0.7;
            double extraGasPrice = emptyTank.isSelected() ? 70.0 : 0.0;
            priceField.setText(Double.toString(price1 + price2 + extraKmPrice + extraGasPrice));
        }
    }

    @FXML
    public void createContract(ActionEvent e) {

        if (rentalTable.getSelectionModel().getSelectedItem() != null && idField.getText() != null){

            try {
                FXMLLoader root = new FXMLLoader(getClass().getResource("/View/RentalContractView.fxml"));
                Scene scene = new Scene(root.load());
                ((RentalContractViewController) root.getController()).setRentalID(Integer.parseInt(idField.getText()));
                ((RentalContractViewController) root.getController()).setTotalPrice(Double.valueOf(priceField.getText()));
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }

    @FXML
    public void updateRental(ActionEvent e) {
        // to do
    }

}
