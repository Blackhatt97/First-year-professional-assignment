package Model;

import Model.DBWrapper.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by CIA on 18/05/2017.
 */
public class ExtrasData {

    private ObservableList<Extras> extrasList = FXCollections.observableArrayList();

    public ObservableList<Extras> getExtrasList() {
        return extrasList;
    }

    public void loadList() {
        DBConn dbConn = new DBConn();
        extrasList = dbConn.getAllExtras();
        dbConn = null;
    }

}
