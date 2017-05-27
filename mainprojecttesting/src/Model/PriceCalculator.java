package Model;

import Model.DBWrapper.DBConn;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PriceCalculator {

    public double getPrice(LocalDate start, LocalDate end, String season, int motorhomeType) {
        DBConn dbConn = new DBConn();
        long daysBetween = ChronoUnit.DAYS.between(start, end);
        double daysBetween1 = daysBetween + 1;
        System.out.println(daysBetween1);
        double seasonMultiplier = 1;
        if (season.equals("Middle")) {
            seasonMultiplier = seasonMultiplier * 1.3;
        } else if (season.equals("Peak")) {
            seasonMultiplier = seasonMultiplier * 1.3 * 1.6;
        }
        double pricePerDay = dbConn.getTypePrice(motorhomeType);
        double totalPrice = pricePerDay * daysBetween1 * seasonMultiplier;
        return totalPrice;
    }

    public double getExtrasPrice(ArrayList<Extras> extras) {
        double total = 0;
        for (Extras extra : extras) {
            total += extra.getPrice();
        }
        return total;
    }

}