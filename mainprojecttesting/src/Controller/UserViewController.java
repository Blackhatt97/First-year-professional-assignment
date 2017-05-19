package Controller;

import Model.ErrorHandler;
import Model.User;
import Model.UserData;
import Model.DBWrapper.DBConn;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Created by blackhatt on 16/05/2017.
 */
public class UserViewController {


    public TableView<User> usersTable;
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

        typeChoiceBox.getItems().addAll("Administrator","Staff","Maintenance");

        usersTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                if(usersTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = usersTable.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                    updateFields((User) selectedItem);
                }
            }
        });
    }
    private void updateFields(User cs) {
        idField.setText(Integer.toString(cs.getId()));
        fNameField.setText(cs.getFname());
        lNameField.setText(cs.getLname());
        birthDatePicker.setValue(cs.getDateBirth().toLocalDate());
        emailField.setText(cs.getEmail());
        addressField.setText(cs.getAddress());
        //typeChoiceBox.getItems().set(4, cs.getType());

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

        java.sql.Date date = java.sql.Date.valueOf(birthDatePicker.getValue());
        dbConn.updateUser(Integer.parseInt(idField.getText()),
                fNameField.getText(),
                lNameField.getText(),
                emailField.getText(),
                (String) typeChoiceBox.getSelectionModel().getSelectedItem(),
                // have to update type too somehow later
                addressField.getText(),
                date);

        loadAllUsers();
    }

    @FXML
    public void create(ActionEvent actionEvent) {
        java.sql.Date datepicker = java.sql.Date.valueOf(birthDatePicker.getValue());

        //Add checkers for integers, add labels to fields in GUI to tell the user which fields have to be filled,
        // say which fields are missing
        //if the user fails to enter stuff into them, if a field is incorrect tell the user which field is incorrect

        String pass = passField.getText();
        String rPass = retypePassField.getText();

        //HERE WE MAKE SECURITY FOR FIELDS AND PASS

        DBConn dbConn = new DBConn();
        dbConn.addUserToDB(fNameField.getText(),
                lNameField.getText(),
                datepicker,
                emailField.getText(),
                addressField.getText(),
                (String) typeChoiceBox.getSelectionModel().getSelectedItem(),
                retypePassField.getText());



        System.out.println("New User Created!");
        loadAllUsers();
        dbConn = null;

    }

    public void delete(ActionEvent event) {
        User selectedRow = usersTable.getSelectionModel().getSelectedItem();
        int usrId = selectedRow.getId();
        DBConn dbConn = new DBConn();
        dbConn.deleteFromDB(usrId, "users");
        loadAllUsers();
        dbConn = null;
    }

    public void resetAll(ActionEvent actionEvent) {
        idField.setText("");
        fNameField.setText("");
        lNameField.setText("");
        emailField.setText("");
        addressField.setText("");
        birthDatePicker.setValue(null);
        typeChoiceBox.setValue(null);
        passField.setText("");
        retypePassField.setText("");
        usersTable.getSelectionModel().select(null);
    }
}
