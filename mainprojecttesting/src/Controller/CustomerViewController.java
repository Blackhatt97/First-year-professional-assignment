package Controller;

import Model.Customer;
import Model.CustomerData;
import Model.DBWrapper.DBConn;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Date;

/**
 * Created by blackhatt on 16/05/2017.
 */
public class CustomerViewController {

    @FXML TextField idField;
    @FXML TextField firstName;
    @FXML TextField lastNameField;
    @FXML TextField emailField;
    @FXML TextField addressField;
    @FXML DatePicker birthDate;
    @FXML Button resetButton;
    @FXML Button createButton;
    @FXML Button updateButton;
    @FXML Button deleteButton;
    @FXML Button loadButton;
    @FXML TableView<Customer> customerTable;

    private CustomerData data = new CustomerData();
    
    @FXML
    public void initialize() {

        loadAllCustomers();

        customerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
                if (customerTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = customerTable.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                    updateFields((Customer) selectedItem);
                }
            }
        });

    }

    private void updateFields(Customer cs) {
        idField.setText(Integer.toString(cs.getId()));
        firstName.setText(cs.getFname());
        lastNameField.setText(cs.getLname());
        birthDate.setValue(cs.getDateBirth().toLocalDate());
        emailField.setText(cs.getEmail());
        addressField.setText(cs.getAddress());
    }

    @FXML
    public void create(ActionEvent actionEvent) {
        if (checkErrors() == 0) {
            resetBorders();
            DBConn dbConn = new DBConn();
            DatePicker datepicker = birthDate;
            java.sql.Date timestamp = java.sql.Date.valueOf(datepicker.getValue());
            dbConn.addCustomerToDB(firstName.getText(),
                    lastNameField.getText(),
                    timestamp,
                    emailField.getText(),
                    addressField.getText());
            loadAllCustomers();
        }
    }
    public void update(ActionEvent actionEvent) {
        if (checkErrors() == 0) {
            resetBorders();
            DatePicker datePicker = birthDate;
            java.sql.Date timestamp = java.sql.Date.valueOf(datePicker.getValue());

            DBConn dbConn = new DBConn();
            dbConn.updateCustomer(Integer.parseInt(idField.getText()),
                    firstName.getText(),
                    lastNameField.getText(),
                    timestamp,
                    addressField.getText(),
                    emailField.getText());
            loadAllCustomers();
        }
    }

    private void loadAllCustomers() {
        data.loadList();
        customerTable.setItems(data.getCustomerList());
        resetAllFields();

    }

    public void delete(ActionEvent actionEvent) {
        if(!customerTable.getSelectionModel().isEmpty()) {
            Customer selectedRow = customerTable.getSelectionModel().getSelectedItem();
            int custId = selectedRow.getId();
            DBConn dbConn = new DBConn();
            dbConn.deleteFromDB(custId, "customers");
        }
        loadAllCustomers();
    }

    public void resetAll(ActionEvent actionEvent) {
        resetAllFields();
    }

    private void resetAllFields() {
        idField.setText("");
        firstName.setText("");
        lastNameField.setText("");
        emailField.setText("");
        addressField.setText("");
        birthDate.setValue(null);
        customerTable.getSelectionModel().select(null);
    }

    public void load(ActionEvent actionEvent) {
        loadAllCustomers();
    }

    private int checkErrors() {
        int counter = 0;
//        if (idField.getText().isEmpty()) {
//            idField.setStyle("-fx-border-color: red;");
//            counter++;
//        }d    d
        if (firstName.getText().isEmpty()) {
            firstName.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (lastNameField.getText().isEmpty()) {
            lastNameField.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (emailField.getText().isEmpty()) {
            emailField.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (addressField.getText().isEmpty()) {
            addressField.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (birthDate.getEditor().getText().isEmpty()) {
            birthDate.setStyle("-fx-border-color: red;");
            counter++;
        }
        return counter;
    }

    private void resetBorders() {
        idField.setStyle("-fx-border-color: transparent");
        firstName.setStyle("-fx-border-color: transparent");
        lastNameField.setStyle("-fx-border-color: transparent");
        emailField.setStyle("-fx-border-color: transparent");
        addressField.setStyle("-fx-border-color: transparent");
        birthDate.setStyle("-fx-border-color: transparent");
    }
}
