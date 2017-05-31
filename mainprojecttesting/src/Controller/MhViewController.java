package Controller;

import Model.*;
import Model.DBWrapper.DBConn;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MhViewController {

    @FXML GridPane gridPaneMh;
    @FXML TextField idField;
    @FXML ChoiceBox<Pair<Integer, String>> statusChoiceBox;
    @FXML TextField plateNumberField;
    @FXML ChoiceBox<Pair<Integer, String>> typeChoiceBox;
    @FXML TextField brandField;
    @FXML TextField fabYearField;
    @FXML TextField kilometrageField;
    @FXML TableView<Motorhome> motorhomeTable;
    @FXML Button resetButton;

    private MotorhomeData data = new MotorhomeData();
    private TypeData typeData = new TypeData();
    private StatusData statusData = new StatusData();

    //this method sets status and type of the motorhome in the relevant fields, it also enables a table listener
    //that updates the fields with the relevant date of a selected motorhome
    @FXML
    public void initialize() {
        loadAllMotorHomes();

        statusChoiceBox.getItems().addAll(statusData.getData());
        typeChoiceBox.getItems().setAll(typeData.getData());

        motorhomeTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Motorhome>() {
            @Override
            public void changed(ObservableValue<? extends Motorhome> observable, Motorhome oldValue, Motorhome newValue) {
                if (motorhomeTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = motorhomeTable.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                    updateFields((Motorhome) selectedItem);
                    resetBorders();
                }
            }
        });
    }

    //this method updates the fields
    private void updateFields(Motorhome mh) {
        idField.setText(Integer.toString(mh.getId()));
        statusChoiceBox.getSelectionModel().select(searchType(mh.getStatus(), statusData));
        plateNumberField.setText(mh.getRegPlate());
        typeChoiceBox.getSelectionModel().select(searchType(mh.getType(), typeData));
        brandField.setText(mh.getBrand());
        fabYearField.setText(Integer.toString(mh.getFabYear()));
        kilometrageField.setText(Integer.toString(mh.getMileage()));
    }

    //this method loops through the types and return the type that was searched for
    private Pair<Integer, String> searchType(int key, DataInterface data) {
        for (Pair<Integer, String> pair : data.getData()) {
            if (pair.getKey() == key) {
                return pair;
            }
        }
        return null;
    }

    //this method loads all of the motorhomes
    private void loadAllMotorHomes() {
        data.loadList();
        motorhomeTable.setItems(data.getMotorhomeList());
    }

    //this method creates a motorhome to the db
    @FXML
    public void create(ActionEvent actionEvent) {
        resetBorders();
        if (checkErrors() == 0) {
            DBConn dbConn = new DBConn();
            dbConn.addMotorHomeToDB(brandField.getText(),
                    Integer.valueOf(fabYearField.getText()),
                    plateNumberField.getText(),
                    Integer.valueOf(kilometrageField.getText()),
                    (Integer) ((Pair) statusChoiceBox.getValue()).getKey(),
                    (Integer) ((Pair) typeChoiceBox.getValue()).getKey());
            loadAllMotorHomes();
            resetBorders();
        }
    }

    //this updates the motorhome in the DB with the values in the fields
    @FXML
    public void update(ActionEvent actionEvent) {
        resetBorders();
        if (checkErrors() == 0 && !idField.getText().isEmpty()) {
            DBConn dbConn = new DBConn();
            dbConn.updateMotorHome(Integer.parseInt(idField.getText()),
                    (Integer) ((Pair) statusChoiceBox.getValue()).getKey(),
                    plateNumberField.getText(),
                    (Integer) ((Pair) typeChoiceBox.getValue()).getKey(),
                    brandField.getText(),
                    Integer.parseInt(fabYearField.getText()),
                    Integer.parseInt(kilometrageField.getText()));
            loadAllMotorHomes();
            resetBorders();
        }
    }

    //this deletes a motorhome from the DB
    @FXML
    public void delete(ActionEvent actionEvent) {
        if(!motorhomeTable.getSelectionModel().isEmpty()) {
            Motorhome selectedCellIndex = motorhomeTable.getSelectionModel().getSelectedItem();
            int motorhome = selectedCellIndex.getId();
            DBConn dbConn = new DBConn();
            dbConn.deleteFromDB(motorhome, "motorhomes");
        }
        loadAllMotorHomes();
        motorhomeTable.refresh();
        resetAllFields();
    }

    //this loads all of the motorhomes from the DB
    @FXML
    public void loadAll(ActionEvent actionEvent) {
        loadAllMotorHomes();
        resetAllFields();
    }

    @FXML
    public void resetAll(ActionEvent actionEvent) {
        resetAllFields();
    }

    //this resets all fields
    private void resetAllFields() {
        motorhomeTable.getSelectionModel().select(null);
        idField.setText("");
        statusChoiceBox.getSelectionModel().select(null);
        plateNumberField.setText("");
        typeChoiceBox.getSelectionModel().select(null);
        brandField.setText("");
        fabYearField.setText("");
        kilometrageField.setText("");
        resetBorders();
    }

    //this resets the borders after an incorrect input
    private void resetBorders() {
        kilometrageField.setStyle("-fx-border-color: transparent");
        fabYearField.setStyle("-fx-border-color: transparent");
        brandField.setStyle("-fx-border-color: transparent");
        typeChoiceBox.setStyle("-fx-border-color: transparent");
        plateNumberField.setStyle("-fx-border-color: transparent");
        statusChoiceBox.setStyle("-fx-border-color: transparent");
        idField.setStyle("-fx-border-color: transparent");
    }

    //this checks for errors in the fields and puts a red border to signify that it is incorrect
    private int checkErrors() {
        int counter = 0;
        if (brandField.getText().isEmpty()) {
            brandField.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (plateNumberField.getText().isEmpty()) {
            plateNumberField.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (statusChoiceBox.getSelectionModel().isEmpty()) {
            statusChoiceBox.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (typeChoiceBox.getSelectionModel().isEmpty()) {
            typeChoiceBox.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (fabYearField.getText().isEmpty()) {
            fabYearField.setStyle("-fx-border-color: red;");
            counter++;
        } else {
            counter += checkInteger(fabYearField);
        }
        if (kilometrageField.getText().isEmpty()) {
            kilometrageField.setStyle("-fx-border-color: red;");
            counter++;
        } else {
            counter += checkInteger(kilometrageField);
        }
        return counter;
    }

    //this checks if integer fields are actual integers
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

