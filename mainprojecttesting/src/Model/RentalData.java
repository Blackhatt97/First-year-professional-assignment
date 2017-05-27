package Model;

import Model.DBWrapper.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RentalData {

    private ObservableList<Rental> rentalList = FXCollections.observableArrayList();

    public ObservableList<Rental> getRentalList() {
        return rentalList;
    }

    public void loadList() {
        DBConn dbConn = new DBConn();
        rentalList = dbConn.getAllRentals();
    }


}
