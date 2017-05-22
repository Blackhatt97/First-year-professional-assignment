package Controller;

import Model.*;
import Model.DBWrapper.DBConn;
import com.sun.xml.internal.rngom.digested.DValuePattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.sql.Connection;


/**
 * Created by blackhatt on 16/05/2017.
 */
public class RepairViewController {

    @FXML TextField idField;
    @FXML ChoiceBox repairTypeField;
    @FXML ChoiceBox mhId;
    @FXML TextField priceField;
    @FXML TextArea descrField;
    @FXML Button resetButton;
    @FXML Button createButton;
    @FXML Button deleteButton;
    @FXML Button updateButton;
    @FXML DatePicker datePick;
    @FXML TableView<Repair> repairTable;
    @FXML ChoiceBox statusBox;

    private RepairData data = new RepairData();
    private RepairTypeData repairTypeData = new RepairTypeData();
    private RepairTypeData typeData = new RepairTypeData();
    private StatusData statusData = new StatusData();
    private MotorhomeData mhData = new MotorhomeData();

    @FXML
    public void initialize(){

        loadAllRepairs();


        repairTypeField.getItems().addAll(typeData.getData());
        statusBox.getItems().addAll(statusData.getData());


        repairTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Repair>() {
            @Override
            public void changed(ObservableValue<? extends Repair> observable, Repair oldValue, Repair newValue) {
                if (repairTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = repairTable.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                    updateFields((Repair) selectedItem);
                }
            }
        });
    }

    private void updateFields(Repair rs) {

        idField.setText(Integer.toString(rs.getId()));
        mhId.getSelectionModel().select(mhData.getMotorhomeList());
        statusBox.getSelectionModel().select(searchType(statusBoxValue(rs.getMhIdFk()),statusData));
        repairTypeField.getSelectionModel().select(searchType(rs.getType(),repairTypeData));
        priceField.setText(String.valueOf(rs.getPrice()));
        datePick.setValue(rs.getRepDate().toLocalDate());
        descrField.setText(rs.getDescription());
    }
    private Pair<Integer, String> searchType(int key, DataInterface data) {
        for (Pair<Integer, String> pair : data.getData()) {
            if (pair.getKey() == key) {
                return pair;
            }
        }
        return null;
    }


    private void loadAllRepairs() {
        data.loadList();
        repairTable.setItems(data.getRepairList());
    }
    public int statusBoxValue(int mhIdFk){
       DBConn dbConn = new DBConn();
       return dbConn.getStatus(mhIdFk);
    }


    public void resetFields(ActionEvent actionEvent) {
        repairTable.getSelectionModel().select(null);
        idField.setText("");
        descrField.setText("");
        priceField.setText("");
        mhId.setValue(null);
        repairTypeField.setValue(null);
        statusBox.setValue(null);
        datePick.setValue(null);
    }

    public void delete(ActionEvent actionEvent) {
        Repair selectedCell = repairTable.getSelectionModel().getSelectedItem();
        int repair = selectedCell.getId();
        DBConn dbConn = new DBConn();
        dbConn.deleteFromDB(repair,"repairs");
        loadAllRepairs();
        resetFields(actionEvent);
        repairTable.refresh();
    }

    public void create(ActionEvent actionEvent) {
        DBConn dbConn = new DBConn();
        java.sql.Date timestamp = java.sql.Date.valueOf(datePick.getValue());
        dbConn.addRepairToDB((Integer) mhId.getValue(),
                (Integer) repairTypeField.getValue(),
                Double.parseDouble(priceField.getText()),
                descrField.getText(),
                timestamp);
        loadAllRepairs();
    }

    public void update(ActionEvent actionEvent) {
        java.sql.Date timestamp = java.sql.Date.valueOf(datePick.getValue());
        DBConn dbConn = new DBConn();
        dbConn.updateRepair(Integer.parseInt(idField.getText()),
                (Integer) mhId.getValue(),
                (Integer) repairTypeField.getValue(),
                Double.parseDouble(priceField.getText()),
                descrField.getText(),
                timestamp);
        loadAllRepairs();
    }
}
