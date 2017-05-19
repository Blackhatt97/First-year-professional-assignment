package Model;

import Model.DBWrapper.DBConn;
import javafx.util.Pair;
import java.util.ArrayList;

public class StatusData implements DataInterface {

    private ArrayList<Pair<Integer, String>> data = null;

    public StatusData() {
        data = loadData();
    }

    public ArrayList<Pair<Integer, String>> loadData() {
        DBConn dbConn = new DBConn();
        return dbConn.getMotorhomeStatuses();
    }

    public ArrayList<Pair<Integer, String>> getData() {
        return this.data;
    }

}
