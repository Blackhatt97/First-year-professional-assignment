package Controller;

import Model.DBWrapper.DBConn;
import Model.Motorhome;
import Model.MotorhomeData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


/**
 * Created by CIA on 15/05/2017.
 */
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

    @FXML
    public void initialize(){
        loadAllMotorHomes();

        statusChoiceBox.getItems().addAll(1, 2, 3, 4, 5, 6); // maybe change it later with more info etc
        typeChoiceBox.getItems().addAll(1, 2, 3, 4, 5, 6); // maybe change it later with more info etc

        motorhomeTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Motorhome>() {
            @Override
            public void changed(ObservableValue<? extends Motorhome> observable, Motorhome oldValue, Motorhome newValue) {
                if(motorhomeTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = motorhomeTable.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                    updateFields((Motorhome) selectedItem);
                }
            }
        });
    }

    private void updateFields(Motorhome mh) {
        idField.setText(Integer.toString(mh.getId()));
        statusChoiceBox.getSelectionModel().select(new Integer(mh.getStatus()));
        plateNumberField.setText(mh.getReg_plate());
        //typeChoiceBox.getSelectionModel().select(); we have no type yet :-??
        brandField.setText(mh.getBrand());
        fabYearField.setText(Integer.toString(mh.getFab_year()));
        kilometrageField.setText(Integer.toString(mh.getMileage()));
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
                1);
        System.out.println("New Motorhome Created!");
        loadAllMotorHomes();
        dbConn = null;

    }
    @FXML

    //TBD by Raz
    public void update(ActionEvent actionEvent) {

	    DBConn dbConn = new DBConn();
        dbConn.updateMotorHome(Integer.parseInt(idField.getText()),
                (Integer) statusChoiceBox.getSelectionModel().getSelectedItem(),
                plateNumberField.getText(),
                1,  // have to update type too somehow later
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
        dbConn.deleteFromDB(motorhome,"motorhomes");
        loadAllMotorHomes();
        motorhomeTable.refresh();
        dbConn = null;

        System.out.println(selectedCellIndex);
    }

    @FXML
    public void loadAll(ActionEvent actionEvent) {
        loadAllMotorHomes();
    }

    @FXML
    public void resetAll(ActionEvent actionEvent) {
        idField.setText("");
        statusChoiceBox.setValue(null);
        plateNumberField.setText("");
        typeChoiceBox.setValue(null);
        brandField.setText("");
        fabYearField.setText("");
        kilometrageField.setText("");
    }

}

