package Model;

import Model.DBWrapper.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MotorhomeData {

    private ObservableList<Motorhome> motorhomeList = FXCollections.observableArrayList();

    public ObservableList<Motorhome> getMotorhomeList() {
        return motorhomeList;
    }

    public void loadList() {
        DBConn dbConn = new DBConn();
        motorhomeList = dbConn.getAllMotorHomes(-1);
    }

    public void loadList(int typeId) {
        DBConn dbConn = new DBConn();
        motorhomeList = dbConn.getAllMotorHomes(typeId);
    }

    public Motorhome searchById(int id) {
        for (Motorhome motorhome : motorhomeList) {
            if (motorhome.getId() == id) {
                return motorhome;
            }
        }
        return null;
    }

}
