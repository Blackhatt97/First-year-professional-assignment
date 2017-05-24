package Model;

import Model.DBWrapper.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by CIA on 25/05/2017.
 */
public class ReservationData {

    private ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

    public ObservableList<Reservation> getReservationList() {
        return reservationList;
    }

    public void loadList() {
        DBConn dbConn = new DBConn();
        reservationList = dbConn.getAllReservations();
    }

}
