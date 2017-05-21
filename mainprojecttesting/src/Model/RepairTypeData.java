package Model;

import Model.DBWrapper.DBConn;
import javafx.util.Pair;
import java.util.ArrayList;

public class RepairTypeData implements DataInterface {

    private ArrayList<Pair<Integer, String>> data = null;

    public RepairTypeData() {
        data = loadData();
    }

    public ArrayList<Pair<Integer, String>> loadData() {
        DBConn dbConn = new DBConn();
        return dbConn.getRepairTypes();
    }

    public ArrayList<Pair<Integer, String>> getData() {
        return this.data;
    }

}
