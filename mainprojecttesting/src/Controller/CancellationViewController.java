package Controller;

import Model.DBWrapper.DBConn;
import Model.PriceCalculator;
import Model.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by CIA on 28/05/2017.
 */
public class CancellationViewController {

    @FXML private TextField cancelText;
    private int reservationID = 0;
    private double initPrice = 0;

    @FXML public void initialize(){

        writeCancellationText();
    }

    public void setInitPrice(double initPrice) {
        this.initPrice = initPrice;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    private void writeCancellationText(){

        DBConn dbConn = new DBConn();
        String intro = "Cancellation details for the reservation of ID: " + reservationID + "\n";
        Reservation reservation = dbConn.getReservationFromDB(reservationID);
        long period = ChronoUnit.DAYS.between(LocalDate.now(), reservation.getStartDate().toLocalDate());
        String periodBeforeStart = "Number of days before starting date: " + period + "\n";
        double percentageCost = 0;
        if (period >= 50){
            percentageCost = 0.2;
        }
        else if (period <= 49 && period >= 15){
            percentageCost = 0.5;
        }
        else if (period < 15){
            percentageCost = 0.85;
        }
        else if(period == 0){
            percentageCost = 0.95;
        }
        String percentageOfPrice = "Percentage amount to pay of total price amounts to: " + percentageCost + "\n";
        double totalPrice = initPrice*percentageCost;
        String cancellationPrice = "Total price for cancellation: " + totalPrice + "\n";

        String totalString = intro + periodBeforeStart + percentageOfPrice + cancellationPrice;
        cancelText.setText(totalString);

    }
}
