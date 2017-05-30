package Controller;

import Model.*;
import Model.DBWrapper.DBConn;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.util.ArrayList;

public class RepairViewController {

    @FXML
    TextField idField;
    @FXML
    ChoiceBox<Pair<Integer, String>> repairTypeField;
    @FXML
    ChoiceBox<Integer> mhId;
    @FXML
    TextField priceField;
    @FXML
    TextArea descrField;
    @FXML
    DatePicker datePick;
    @FXML
    TableView<Repair> repairTable;
    @FXML
    ChoiceBox<Pair<Integer, String>> statusBox;

    private RepairData data = new RepairData();
    private RepairData mhRepairData = new RepairData();
    private RepairTypeData repairTypeData = new RepairTypeData();
    private RepairTypeData typeData = new RepairTypeData();
    private StatusData statusData = new StatusData();
    private MotorhomeData mhData = new MotorhomeData();
    private ArrayList<Integer> mhIds = new ArrayList<>();

    @FXML
    public void initialize() {
        loadAllRepairs();

        mhData.loadList();
        repairTypeField.getItems().addAll(typeData.getData());
        statusBox.getItems().addAll(statusData.getData());
        mhId.getItems().addAll(mhIds);

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

    private void loadMotorhomeIds() {
        DBConn dbConn = new DBConn();
        mhIds = dbConn.getMotorhomeId();
    }

    private void updateFields(Repair repair) {
        idField.setText(Integer.toString(repair.getId()));
        mhId.getSelectionModel().select(repair.getMhId());
        statusBox.getSelectionModel().select(searchType(statusBoxValue(repair.getMhId()), statusData));
        repairTypeField.getSelectionModel().select(searchType(repair.getType(), repairTypeData));
        priceField.setText(String.valueOf(repair.getPrice()));
        datePick.setValue(repair.getRepDate().toLocalDate());
        descrField.setText(repair.getDescription());
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
        loadMotorhomeIds();
    }

    private int statusBoxValue(int mhId) {
        DBConn dbConn = new DBConn();
        return dbConn.getStatus(mhId);
    }

    @FXML
    public void resetFields(ActionEvent actionEvent) {
        resetAllFields();
    }

    private void resetAllFields() {
        repairTable.getSelectionModel().select(null);
        idField.setText("");
        descrField.setText("");
        priceField.setText("");
        mhId.setValue(null);
        repairTypeField.setValue(null);
        statusBox.setValue(null);
        datePick.setValue(null);
        resetBorders();
    }

    @FXML
    public void delete(ActionEvent actionEvent) {
        if (!repairTable.getSelectionModel().isEmpty()) {
            Repair selectedCell = repairTable.getSelectionModel().getSelectedItem();
            int repair = selectedCell.getId();
            DBConn dbConn = new DBConn();
            dbConn.deleteFromDB(repair, "repairs");
        }
        loadAllRepairs();
        resetAllFields();
        repairTable.refresh();
    }

    @FXML
    public void create(ActionEvent actionEvent) {
        resetBorders();
        if (checkErrors() == 0) {
            DBConn dbConn = new DBConn();
            java.sql.Date timestamp = java.sql.Date.valueOf(datePick.getValue());
            dbConn.addRepairToDB(
                    mhId.getValue(),
                    repairTypeField.getValue().getKey(),
                    Double.parseDouble(priceField.getText()),
                    descrField.getText(),
                    timestamp
            );
            loadAllRepairs();
        }
    }

    @FXML
    public void update(ActionEvent actionEvent) {
        resetBorders();
        Repair repair = repairTable.getSelectionModel().getSelectedItem();
        if (checkErrors() == 0 && repair != null) {
            java.sql.Date timestamp = java.sql.Date.valueOf(datePick.getValue());
            DBConn dbConn = new DBConn();
            dbConn.updateRepair(
                    repair.getId(),
                    mhId.getValue(),
                    repairTypeField.getValue().getKey(),
                    Double.parseDouble(priceField.getText()),
                    descrField.getText(),
                    timestamp,
                    statusBox.getValue().getKey()
            );
            loadAllRepairs();
        }
    }

    @FXML
    public void loadMotorhomeLog(ActionEvent actionEvent) {
        if (!mhId.getSelectionModel().isEmpty()) {
            mhRepairData.loadList(mhId.getValue());
            repairTable.setItems(mhRepairData.getRepairList());
        }
    }

    private void resetBorders() {
        repairTypeField.setStyle("-fx-border-color: transparent");
        priceField.setStyle("-fx-border-color: transparent");
        mhId.setStyle("-fx-border-color: transparent");
        datePick.setStyle("-fx-border-color: transparent");
        statusBox.setStyle("-fx-border-color: transparent");
    }

    private int checkErrors() {
        int counter = 0;
        if (mhId.getSelectionModel().isEmpty()) {
            mhId.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (repairTypeField.getSelectionModel().isEmpty()) {
            repairTypeField.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (datePick.getEditor().getText().isEmpty()) {
            datePick.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (statusBox.getSelectionModel().isEmpty()) {
            statusBox.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (priceField.getText().isEmpty()) {
            priceField.setStyle("-fx-border-color: red;");
            counter++;
        } else {
            counter += checkDouble(priceField);
        }
        return counter;
    }

    private int checkDouble(TextField field) {
        try {
            Double.parseDouble(field.getText());
        } catch (NumberFormatException ex) {
            field.setStyle("-fx-border-color: red;");
            return 1;
        }
        return 0;
    }
}