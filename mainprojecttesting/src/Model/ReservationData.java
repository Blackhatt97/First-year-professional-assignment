package Model;

import Model.DBWrapper.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Created by CIA on 25/05/2017.
 */
public class ReservationData {

    private ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
    private ArrayList<String> seasons;

    public ReservationData() {
        seasons = new ArrayList<>();
        this.seasons.add("Low");
        this.seasons.add("Middle");
        this.seasons.add("Peak");
    }

    public ArrayList<String> getSeasons() {
        return seasons;
    }

    public ObservableList<Reservation> getReservationList() {
        return reservationList;
    }

    public void loadList() {
        DBConn dbConn = new DBConn();
        reservationList = dbConn.getAllReservations();
    }

    public double getPrice(LocalDate start, LocalDate end, String season, int motorhomeType, DBConn dbConn){

            long daysBetween = ChronoUnit.DAYS.between(start, end);
            double daysBetween1 = daysBetween + 1;
            System.out.println(daysBetween1);
            double seasonMultiplier = 1;
            if (season.equals("Middle")){
                seasonMultiplier = seasonMultiplier*1.3;
            }
            else if (season.equals("Peak")){
                seasonMultiplier = seasonMultiplier*1.3*1.6;
            }
            double pricePerDay = dbConn.getTypePrice(motorhomeType);
            double totalPrice = pricePerDay*daysBetween1*seasonMultiplier;
            return totalPrice;
    }

}
