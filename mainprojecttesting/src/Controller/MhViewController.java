package Controller;

import Model.DBWrapper.DBConn;
import Model.Motorhome;
import Model.MotorhomeData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


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

    private MotorhomeData data = new MotorhomeData();

    @FXML
    public void initialize(){
        loadAllMotorHomes();
    }

    public void loadAllMotorHomes() {
        data.loadList();
        motorhomeTable.setItems(data.getMotorhomeList());
    }
    //Vaidaras
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
//TBD by Raz
    public void update(ActionEvent actionEvent) {



    }

    //Bogdan
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

    public void loadAll(ActionEvent actionEvent) {
        loadAllMotorHomes();
    }

    public void resetAll(ActionEvent actionEvent) {
    }

}

