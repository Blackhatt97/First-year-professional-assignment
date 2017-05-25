package Model;

import Model.DBWrapper.DBConn;
import javafx.util.Pair;
import java.util.ArrayList;

public class TypeData implements DataInterface {

    private ArrayList<Pair<Integer, String>> data = null;

    public TypeData() {
        data = loadData();
    }

    public ArrayList<Pair<Integer, String>> loadData() {
        DBConn dbConn = new DBConn();
        return dbConn.getMotorhomeTypes();
    }

    public ArrayList<Pair<Integer, String>> getData() {
        return this.data;
    }

    public Pair<Integer, String> searchById(int id) {
        for (Pair<Integer, String> pair : data) {
            if (pair.getKey() == id) {
                return pair;
            }
        }
        return null;
    }

}
