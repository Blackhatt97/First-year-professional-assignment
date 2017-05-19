package Controller;

import Model.*;
import Model.DBWrapper.DBConn;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;

public class MhViewController {

    @FXML TextField idField;
    @FXML ChoiceBox statusChoiceBox;
    @FXML TextField plateNumberField;
    @FXML ChoiceBox typeChoiceBox;
    @FXML TextField brandField;
    @FXML TextField fabYearField;
    @FXML TextField kilometrageField;
    @FXML TableView<Motorhome> motorhomeTable;
    @FXML Button resetButton;

    private MotorhomeData data = new MotorhomeData();
    private TypeData typeData = new TypeData();
    private StatusData statusData = new StatusData();

    @FXML
    public void initialize() {
        loadAllMotorHomes();

        statusChoiceBox.getItems().addAll(statusData.getData()); // maybe change it later with more info etc
        typeChoiceBox.getItems().setAll(typeData.getData());

        motorhomeTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Motorhome>() {
            @Override
            public void changed(ObservableValue<? extends Motorhome> observable, Motorhome oldValue, Motorhome newValue) {
                if (motorhomeTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = motorhomeTable.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                    updateFields((Motorhome) selectedItem);
                }
            }
        });
    }

    private void updateFields(Motorhome mh) {
        idField.setText(Integer.toString(mh.getId()));
        statusChoiceBox.getSelectionModel().select(searchType(mh.getStatus(), statusData));
        plateNumberField.setText(mh.getRegPlate());
        typeChoiceBox.getSelectionModel().select(searchType(mh.getType(), typeData));
        brandField.setText(mh.getBrand());
        fabYearField.setText(Integer.toString(mh.getFabYear()));
        kilometrageField.setText(Integer.toString(mh.getMileage()));
    }

    private Pair<Integer, String> searchType(int key, DataInterface data) {
        for (Pair<Integer, String> pair : data.getData()) {
            if (pair.getKey() == key) {
                return pair;
            }
        }
        return null;
    }

    private void loadAllMotorHomes() {
        data.loadList();
        motorhomeTable.setItems(data.getMotorhomeList());
    }

    //Vaidaras
    @FXML
    public void create(ActionEvent actionEvent) {

        //Add checkers for integers, add labels to fields in GUI to tell the user which fields have to be filled, say which fields are missing
        //if the user fails to enter stuff into them, if a field is incorrect tell the user which field is incorrect
        DBConn dbConn = new DBConn();
        dbConn.addMotorHomeToDB(brandField.getText(),
                Integer.valueOf(fabYearField.getText()),
                plateNumberField.getText(),
                Integer.valueOf(kilometrageField.getText()),
                (Integer) ((Pair) statusChoiceBox.getValue()).getKey(),
                (Integer) ((Pair) typeChoiceBox.getValue()).getKey());
        loadAllMotorHomes();

    }

    //TBD by Raz
    @FXML
    public void update(ActionEvent actionEvent) {

	    DBConn dbConn = new DBConn();
        dbConn.updateMotorHome(Integer.parseInt(idField.getText()),
                (Integer)((Pair)statusChoiceBox.getValue()).getKey(),
                plateNumberField.getText(),
                (Integer)((Pair)typeChoiceBox.getValue()).getKey(),
                brandField.getText(),
                Integer.parseInt(fabYearField.getText()),
                Integer.parseInt(kilometrageField.getText()));
        loadAllMotorHomes();

    }

    //Bogdan
    @FXML
    public void delete(ActionEvent actionEvent) {
        Motorhome selectedCellIndex = motorhomeTable.getSelectionModel().getSelectedItem();
        int motorhome = selectedCellIndex.getId();
        DBConn dbConn = new DBConn();
        dbConn.deleteFromDB(motorhome, "motorhomes");
        loadAllMotorHomes();
        motorhomeTable.refresh();
    }

    @FXML
    public void loadAll(ActionEvent actionEvent) {
        loadAllMotorHomes();
    }

    @FXML
    public void resetAll(ActionEvent actionEvent) {
        motorhomeTable.getSelectionModel().select(null);
        idField.setText("");
        statusChoiceBox.setValue(null);
        plateNumberField.setText("");
        typeChoiceBox.setValue(null);
        brandField.setText("");
        fabYearField.setText("");
        kilometrageField.setText("");
    }

}

