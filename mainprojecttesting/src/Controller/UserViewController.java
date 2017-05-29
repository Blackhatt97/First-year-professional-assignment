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
    public ChoiceBox<String> typeChoiceBox;
    public PasswordField passField;
    public PasswordField retypePassField;


    private UserData data = new UserData();


    @FXML
    public void initialize() {

        loadAllUsers();
        choiceBoxPresets();

        usersTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                if(usersTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = usersTable.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                    updateFields((User) selectedItem);
                    passField.setText("");
                    retypePassField.setText("");
                    resetBorders();
                }
            }
        });
    }

    private void choiceBoxPresets() {
        typeChoiceBox.getItems().addAll("Administrator","Staff","Maintenance");
    }

    private void updateFields(User cs) {
        idField.setText(Integer.toString(cs.getId()));
        fNameField.setText(cs.getFname());
        lNameField.setText(cs.getLname());
        birthDatePicker.setValue(cs.getDateBirth().toLocalDate());
        emailField.setText(cs.getEmail());
        addressField.setText(cs.getAddress());
        typeChoiceBox.setValue(cs.getType());
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
        resetBorders();
        if (passField.getText().isEmpty() && retypePassField.getText().isEmpty()) {
            if (checkErrorsNoPassword() == 0 && !idField.getText().isEmpty()) {
                DBConn dbConn = new DBConn();
                java.sql.Date date = java.sql.Date.valueOf(birthDatePicker.getValue());
                dbConn.updateUser(Integer.parseInt(idField.getText()),
                        fNameField.getText(),
                        lNameField.getText(),
                        emailField.getText(),
                        typeChoiceBox.getSelectionModel().getSelectedItem(),
                        addressField.getText(),
                        date);
            }
            loadAllUsers();
        } else {
            if (checkErrors() + checkErrorsNoPassword() == 0 && !idField.getText().isEmpty()) {
                if (retypePassField.getText().equals(passField.getText())) {
                    String newPass = passField.getText();
                    DBConn dbConn = new DBConn();
                    java.sql.Date date = java.sql.Date.valueOf(birthDatePicker.getValue());
                    dbConn.updateUser(Integer.parseInt(idField.getText()),
                            fNameField.getText(),
                            lNameField.getText(),
                            emailField.getText(),
                            typeChoiceBox.getSelectionModel().getSelectedItem(),
                            addressField.getText(),
                            date,
                            newPass);
                } else {
                    ErrorHandler.popUp("User update", "The passwords don't match", "Retype your Password");
                }

                loadAllUsers();
            }
        }
    }

    @FXML
    public void create(ActionEvent actionEvent) {
        resetBorders();
        if (checkErrors() + checkErrorsNoPassword() == 0) {
            //HERE WE MAKE SECURITY FOR FIELDS AND PASS
            if (retypePassField.getText().equals(passField.getText())) {

                java.sql.Date datepicker = java.sql.Date.valueOf(birthDatePicker.getValue());
                DBConn dbConn = new DBConn();
                dbConn.addUserToDB(fNameField.getText(),
                        lNameField.getText(),
                        datepicker,
                        emailField.getText(),
                        addressField.getText(),
                        typeChoiceBox.getSelectionModel().getSelectedItem(),
                        passField.getText());

                System.out.println("New User Created!");
            } else {
                ErrorHandler.popUp("User creation", "The passwords don't match", "Retype your Password");
            }
            loadAllUsers();
        }
    }

    public void delete(ActionEvent event) {
        if(!usersTable.getSelectionModel().isEmpty()) {
            User selectedRow = usersTable.getSelectionModel().getSelectedItem();
            int usrId = selectedRow.getId();
            DBConn dbConn = new DBConn();
            dbConn.deleteFromDB(usrId, "users");
        } else {
            System.out.println("Selection empty");
        }
        loadAllUsers();
        resetAllFields();
    }

    public void resetAll(ActionEvent actionEvent) {
        resetAllFields();
    }

    private void resetAllFields() {
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

    private void resetBorders() {
        fNameField.setStyle("-fx-border-color: transparent");
        lNameField.setStyle("-fx-border-color: transparent");
        typeChoiceBox.setStyle("-fx-border-color: transparent");
        emailField.setStyle("-fx-border-color: transparent");
        addressField.setStyle("-fx-border-color: transparent");
        birthDatePicker.setStyle("-fx-border-color: transparent");
        passField.setStyle("-fx-border-color: transparent");
        retypePassField.setStyle("-fx-border-color: transparent");
    }

    private int checkErrors() {
        int counter = 0;
        if (passField.getText().isEmpty()) {
            passField.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (retypePassField.getText().isEmpty()){
            retypePassField.setStyle("-fx-border-color: red;");
            counter++;
        }
        return counter;
    }

    private int checkErrorsNoPassword() {
        int counter = 0;
        if (fNameField.getText().isEmpty()) {
            fNameField.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (lNameField.getText().isEmpty()) {
            lNameField.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (typeChoiceBox.getSelectionModel().isEmpty()) {
            typeChoiceBox.setStyle("-fx-border-color: red;");
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
        return counter;
    }
}
