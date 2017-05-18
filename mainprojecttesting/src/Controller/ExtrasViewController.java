package Controller;

import Model.DBWrapper.DBConn;
import Model.Extras;
import Model.ExtrasData;
import Model.ExtrasTypes;
import Model.Motorhome;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Created by blackhatt on 16/05/2017.
 */
public class ExtrasViewController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField unitsField;
    @FXML private TextField priceField;
    @FXML private ChoiceBox<ExtrasTypes> typeChoiceBox;
    @FXML private TableView<Extras> extrasTable;

    ExtrasData extrasData = new ExtrasData();

    @FXML public void initialize(){

        loadAll();
        typeChoiceBox.getItems().addAll(ExtrasTypes.values());

        extrasTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Extras>() {
            @Override
            public void changed(ObservableValue<? extends Extras> observable, Extras oldValue, Extras newValue) {
                if(extrasTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = extrasTable.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                    updateFields((Extras) selectedItem);
                }
            }
        });

    }

    public void updateFields(Extras extras){

        idField.setText(String.valueOf(extras.getId()));
        nameField.setText(extras.getName());
        priceField.setText(String.valueOf(extras.getPrice()));
        typeChoiceBox.getSelectionModel().select(ExtrasTypes.valueOf(extras.getType()));

    }

    public void createExtra(ActionEvent actionEvent) {
        //add checker correct inputs for fields
        DBConn dbConn = new DBConn();
        dbConn.addExtrasToDB(typeChoiceBox.getSelectionModel().getSelectedItem().toString(),
                nameField.getText(),
                Double.parseDouble(priceField.getText()),
                0);
        dbConn = null;
        loadAll();
    }

    public void loadAllExtras(ActionEvent actionEvent) {
        loadAll();
    }

    public void loadAll(){

        extrasData.loadList();
        extrasTable.setItems(extrasData.getExtrasList());
        extrasTable.refresh();

    }

    public void resetFields(ActionEvent actionEvent) {
    }

    public void updateExtra(ActionEvent actionEvent) {
        //check for double
        DBConn dbConn = new DBConn();
        Extras extras = extrasTable.getSelectionModel().getSelectedItem();
        int idOfExtra = extras.getId();
        dbConn.updateExtras(idOfExtra,
                nameField.getText(),
                typeChoiceBox.getSelectionModel().getSelectedItem().toString(),
                Double.parseDouble(priceField.getText()));
        dbConn = null;
        loadAll();

    }

    public void deleteExtra(ActionEvent actionEvent) {
    }
}
