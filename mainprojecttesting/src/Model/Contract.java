package Model;

import Model.DBWrapper.DBConn;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by CIA on 28/05/2017.
 */
public class Contract {

    public String createCancellationText(int reservationID, double initPrice ){

        DBConn dbConn = new DBConn();
        String intro = "Cancellation details for the reservation of ID: " + reservationID + "\n";
        Reservation reservation = dbConn.getReservationFromDB(reservationID);
        String dayOfReservation = "Day of reservation: " + reservation.getReservationDate() + "\n";
        String customerDetails = "Customer ID: " + reservation.getCustId() + "\n";
        String motorhomeDetails = "Motorhome ID: " + reservation.getMotorhomeId() + "\n";
        String startingAndEndDates = "Starting date: " + reservation.getStartDate().toString() + " | Ending date: " +
                reservation.getEndDate().toString() + "\n";
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
        int periodInt = Math.toIntExact(period);
        if(periodInt == 0){
            percentageCost = 0.95;
        }
        String percentageOfPrice = "Percentage of total price amounts to: " + percentageCost + "\n";
        double totalPrice = initPrice*percentageCost;
        String cancellationPrice = "Total price for cancellation: " + totalPrice + "\n";

        String totalString = intro + dayOfReservation + customerDetails + motorhomeDetails + startingAndEndDates  +
                periodBeforeStart + percentageOfPrice + cancellationPrice;
        dbConn = null;
        return totalString;
    }

}
