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
    public void initialize(){

        loadAllCustomers();

        customerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
                if(customerTable.getSelectionModel().getSelectedItem() != null) {
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

        //Add checkers for integers, add labels to fields in GUI to tell the user which fields have to be filled, say which fields are missing
        //if the user fails to enter stuff into them, if a field is incorrect tell the user which field is incorrect
        DBConn dbConn = new DBConn();
        DatePicker datepicker = birthDate;
//        LocalDate localdate = datepicker.getValue();
//        Instant instant = Instant.from(localdate.atStartOfDay(ZoneId.systemDefault()));
//        Date date = Date.from(instant);
//        java.sql.Timestamp timestamp = java.sql.Timestamp.from(date.toInstant());
        java.sql.Date timestamp = java.sql.Date.valueOf(datepicker.getValue());
        dbConn.addCustomerToDB(firstName.getText(),
                lastNameField.getText(),
                timestamp,
                emailField.getText(),
                addressField.getText());
        System.out.println("New Motorhome Created!");
        loadAllCustomers();
        dbConn = null;

    }
//    public void update(ActionEvent actionEvent) {
//
//        DBConn dbConn = new DBConn();
//        dbConn.updateCustomer(Integer.parseInt(idField.getText()),
//                (Integer) statusChoiceBox.getSelectionModel().getSelectedItem(),
//                plateNumberField.getText(),
//                1,  // have to update type too somehow later
//                brandField.getText(),
//                Integer.parseInt(fabYearField.getText()),
//                Integer.parseInt(kilometrageField.getText()));
//        loadAllCustomers();
//
//    }

    private void loadAllCustomers() {
        data.loadList();
        customerTable.setItems(data.getCustomerList());
    }

    public void update(ActionEvent actionEvent) {
    }

    public void delete(ActionEvent actionEvent) {
    }

    public void resetAll(ActionEvent actionEvent) {
    }

    public void load(ActionEvent actionEvent) {
    }
}
