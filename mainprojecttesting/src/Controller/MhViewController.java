package Controller;

import Model.DBWrapper.DBConn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
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
    DBConn dbConn = null;


    @FXML
    public void create(ActionEvent actionEvent) {

        //Add checkers for integers, add labels to fields in GUI to tell the user which fields have to be filled, say which fields are missing
        //if the user fails to enter stuff into them, if a field is incorrect tell the user which field is incorrect
        dbConn = new DBConn();
        dbConn.addMotorHomeToDB(brandField.getText(),
                Integer.valueOf(fabYearField.getText()),
                plateNumberField.getText(),
                Integer.valueOf(kilometrageField.getText()),
                1);
        System.out.println("New Motorhome Created!");
        dbConn = null;

    }

    public void update(ActionEvent actionEvent) {
    }

    public void delete(ActionEvent actionEvent) {
    }
}