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

public class ExtrasViewController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private ChoiceBox<ExtrasTypes> typeChoiceBox;
    @FXML private TableView<Extras> extrasTable;

    private ExtrasData extrasData = new ExtrasData();

    @FXML
    public void initialize() {

        loadAll();
        typeChoiceBox.getItems().addAll(ExtrasTypes.values());

        extrasTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Extras>() {
            @Override
            public void changed(ObservableValue<? extends Extras> observable, Extras oldValue, Extras newValue) {
                if (extrasTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = extrasTable.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                    updateFields((Extras) selectedItem);
                }
            }
        });

    }

    private void updateFields(Extras extras) {
        idField.setText(String.valueOf(extras.getId()));
        nameField.setText(extras.getName());
        priceField.setText(String.valueOf(extras.getPrice()));
        typeChoiceBox.getSelectionModel().select(ExtrasTypes.valueOf(extras.getType()));
    }

    @FXML
    public void createExtra(ActionEvent actionEvent) {
        resetBorders();
        if (checkErrors() == 0) {
            DBConn dbConn = new DBConn();
            dbConn.addExtrasToDB(typeChoiceBox.getSelectionModel().getSelectedItem().toString(),
                    nameField.getText(),
                    Double.parseDouble(priceField.getText()),
                    0);
            loadAll();
        }
    }

    @FXML
    public void loadAllExtras(ActionEvent actionEvent) {
        loadAll();
        resetAllFields();
    }

    private void loadAll() {
        extrasData.loadList();
        extrasTable.setItems(extrasData.getExtrasList());
    }

    @FXML
    public void resetFields(ActionEvent actionEvent) {
        resetAllFields();
    }

    private void resetAllFields() {
        idField.setText("");
        nameField.setText("");
        typeChoiceBox.getSelectionModel().select(null);
        priceField.setText("");
    }

    @FXML
    public void updateExtra(ActionEvent actionEvent) {
        resetBorders();
        Extras extra = extrasTable.getSelectionModel().getSelectedItem();
        if (extra != null && !idField.getText().isEmpty() && checkErrors() == 0) {
            DBConn dbConn = new DBConn();
            dbConn.updateExtras(
                    extra.getId(),
                    nameField.getText(),
                    typeChoiceBox.getSelectionModel().getSelectedItem().toString(),
                    Double.parseDouble(priceField.getText())
            );
            loadAll();
        }
    }

    @FXML
    public void deleteExtra(ActionEvent actionEvent) {
        resetBorders();
        Extras extra = extrasTable.getSelectionModel().getSelectedItem();
        if (extra != null) {
            DBConn dbConn = new DBConn();
            dbConn.deleteFromDB(extra.getId(), "extras");
        }
        resetAllFields();
        loadAll();
    }

    private void resetBorders() {
        nameField.setStyle("-fx-border-color: transparent");
        priceField.setStyle("-fx-border-color: transparent");
        typeChoiceBox.setStyle("-fx-border-color: transparent");
    }

    private int checkErrors() {
        int counter = 0;
        if (nameField.getText().isEmpty()) {
            nameField.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (typeChoiceBox.getSelectionModel().isEmpty()) {
            typeChoiceBox.setStyle("-fx-border-color: red;");
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
