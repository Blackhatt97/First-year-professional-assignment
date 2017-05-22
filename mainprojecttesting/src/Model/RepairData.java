package Model;

import Model.DBWrapper.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Observable;

/**
 * Created by blackhatt on 21/05/2017.
 */
public class RepairData {

    private ObservableList<Repair> repairList = FXCollections.observableArrayList();

    public ObservableList<Repair> getRepairList() {
        return repairList;
    }

    public void loadList() {

        DBConn dbConn = new DBConn();
        repairList = dbConn.getAllRepairs();
        dbConn = null;

    }
}
