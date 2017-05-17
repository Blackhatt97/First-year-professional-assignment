package Controller;

import Model.DBWrapper.DBConn;
import Model.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sun.security.jca.GetInstance;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by blackhatt on 16/05/2017.
 */
public class UserViewController {


    public TableView usersTable;
    public TextField idField;
    public TextField fNameField;
    public TextField lNameField;
    public DatePicker birthDatePicker;
    public TextField emailField;
    public TextField addressField;
    public ChoiceBox typeChoiceBox;
    public PasswordField passField;
    public PasswordField retypePassField;


    private UserData data = new UserData();


    @FXML
    public void initialize() {
        loadAllUsers();
    }

    private void loadAllUsers() {
        data.loadList();
        usersTable.setItems(data.getUserList());
    }

    @FXML
    public void loadAll (ActionEvent actionEvent) {
        loadAllUsers();
    }
    @FXML
    public void update(ActionEvent actionEvent) {

        DBConn dbConn = new DBConn();
        java.sql.Date datepicker = java.sql.Date.valueOf(birthDatePicker.getValue());
        dbConn.updateUser(Integer.parseInt(idField.getText()),
                fNameField.getText(),
                lNameField.getText(),
                emailField.getText(),
                (String) typeChoiceBox.getSelectionModel().getSelectedItem(),
                // have to update type too somehow later
                addressField.getText(),
                datepicker);

        loadAllUsers();
    }

    @FXML
    public void create(ActionEvent actionEvent) {
        java.sql.Date datepicker = java.sql.Date.valueOf(birthDatePicker.getValue());
        //Add checkers for integers, add labels to fields in GUI to tell the user which fields have to be filled, say which fields are missing
        //if the user fails to enter stuff into them, if a field is incorrect tell the user which field is incorrect
        DBConn dbConn = new DBConn();
        dbConn.addUserToDB(fNameField.getText(),
                lNameField.getText(),
                datepicker,
                emailField.getText(),
                addressField.getText(),
                (String) typeChoiceBox.getSelectionModel().getSelectedItem(),
                retypePassField.getText());

        System.out.println("New Motorhome Created!");
        loadAllUsers();
        dbConn = null;

    }
}
