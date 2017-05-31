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

    private RentalData rentalData = new RentalData();

    @FXML
    //this method is executed upon the initialization of the controller and loads all rentals from DB into the table view
    //it also enables a listener for the table that updates the fields with the selected item in the table
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

    //this updates the fields with rental details
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
        loadRentalExtras();
    }

    //this method creates a popup where extras can be added to the rental
    @FXML
    public void addExtras(ActionEvent e) {
        Rental rental = rentalTable.getSelectionModel().getSelectedItem();
        if (rental != null && !idField.getText().isEmpty()) {
            try {
                FXMLLoader root = new FXMLLoader(getClass().getResource("/View/ExtrasPopupView.fxml"));
                Scene scene = new Scene(root.load());
                ((ExtrasPopupController) root.getController()).setRentalId(rental.getId());
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

    //this loads all added extras for a specific rental
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

    //this loads all rentals from the db
    private void loadAllRentals() {
        rentalData.loadList();
        rentalTable.setItems(rentalData.getRentalList());
        resetAllFields();
    }

    @FXML
    public void loadRentals(ActionEvent e) {
        loadAllRentals();
    }

    @FXML
    public void resetFields(ActionEvent e) {
        resetAllFields();
    }

    //this resets all fields to empty values
    private void resetAllFields() {
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

    //this calculates the price of a rental and sets the price field to the result
    @FXML
    public void calculatePrice(ActionEvent e) {
        loadRentalExtras();
        Rental rental = rentalTable.getSelectionModel().getSelectedItem();
        if (rental != null) {
            priceField.setText(Double.toString(calculatePricing(rental)));
        }
    }

    //this takes in all relevant values to calculate a price for a rental and returns a price
    private double calculatePricing(Rental rental) {
        DBConn dbConn = new DBConn();
        PriceCalculator priceCalculator = new PriceCalculator();
        return priceCalculator.getTotalPrice(
                rental.getStartDate().toLocalDate(),
                rental.getEndDate().toLocalDate(),
                rental.getSeason(),
                dbConn.getMotorhomeType(rental.getMotorhomeId()),
                rental.getExtra(),
                Integer.parseInt(extraKmField.getText()),
                Integer.parseInt(dropoffField.getText()),
                Integer.parseInt(pickupField.getText()),
                emptyTank.isSelected()
        );
    }

    //this method brings up a popup that shows the rental contract
    @FXML
    public void createContract(ActionEvent e) {
        resetBorders();
        Rental rental = rentalTable.getSelectionModel().getSelectedItem();
        if (rental != null && !idField.getText().isEmpty() && checkErrors() == 0) {
            try {
                FXMLLoader root = new FXMLLoader(getClass().getResource("/View/RentalContractView.fxml"));
                Scene scene = new Scene(root.load());
                ((RentalContractViewController) root.getController()).setRentalID(rental.getId());
                ((RentalContractViewController) root.getController()).setTotalPrice(calculatePricing(rental));
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //this updates a rentals pickup and dropoff values in the DB
    @FXML
    public void updateRental(ActionEvent e) {
        resetBorders();
        Rental rental = rentalTable.getSelectionModel().getSelectedItem();
        if (rental != null && !idField.getText().isEmpty() &&
                checkInteger(pickupField) == 0 && checkInteger((dropoffField)) == 0) {
            DBConn dbConn = new DBConn();
            dbConn.updateRental(Integer.parseInt(pickupField.getText()),
                    Integer.parseInt(dropoffField.getText()),
                    rental.getId());
            loadAllRentals();
        }
    }

    //this resets borders to transparent after an incorrect input has been corrected
    private void resetBorders() {
        pickupField.setStyle("-fx-border-color: transparent");
        dropoffField.setStyle("-fx-border-color: transparent");
        extraKmField.setStyle("-fx-border-color: transparent");
        priceField.setStyle("-fx-border-color: transparent");
    }

    //this method checks fields for incorrect inputs
    private int checkErrors() {
        int counter = 0;
        if (pickupField.getText().isEmpty()) {
            pickupField.setStyle("-fx-border-color: red;");
            counter++;
        } else {
            counter += checkInteger(pickupField);
        }
        if (dropoffField.getText().isEmpty()) {
            dropoffField.setStyle("-fx-border-color: red;");
            counter++;
        } else {
            counter += checkInteger(dropoffField);
        }
        if (extraKmField.getText().isEmpty()) {
            extraKmField.setStyle("-fx-border-color: red;");
            counter++;
        } else {
            counter += checkInteger(extraKmField);
        }
        if (priceField.getText().isEmpty()) {
            priceField.setStyle("-fx-border-color: red;");
            counter++;
        }
        return counter;
    }

    private int checkInteger(TextField field) {
        try {
            Integer.parseInt(field.getText());
        } catch (NumberFormatException ex) {
            field.setStyle("-fx-border-color: red;");
            return 1;
        }
        return 0;
    }

}
