package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Created by blackhatt on 16/05/2017.
 */
public class ReservationViewController {

    @FXML DatePicker reservationPicker;
    ArrayList<LocalDate> range = new ArrayList<>();
    boolean dateButtonClicked = false;
    int dateButtonClicks = 0;

    @FXML public void initialize(){
        //add css coloring for the datepicker between the ranges
        reservationPicker.setOnAction(event -> {
            if (dateButtonClicked){
                dateButtonClicked = false;
            }
            LocalDate date = reservationPicker.getValue();
            range.add(date);
            dateButtonClicks++;
            System.out.println("Selected date: " + date);
            if (dateButtonClicks == 2 ){
                System.out.println("FIRST DATE: " + range.get(0) + " | SECOND DATE: " + range.get(1));
                calcPeriod();
                dateButtonClicks++;
            }
            if (dateButtonClicks > 2){
                range.clear();
                dateButtonClicks = 0;
            }
        });
        reservationPicker.setOnHidden(event -> {
            if(!dateButtonClicked){
                reservationPicker.show();
                dateButtonClicked = true;
            }
        });
    }

    public void chooseDate(MouseEvent mouseEvent) {

//        LocalDate date = reservationPicker.getValue();
//        System.out.println(date.toString());

    }

    public void calcPeriod(){


        long p2 = ChronoUnit.DAYS.between(range.get(0), range.get(1));
        System.out.println("TOTAL DAYS: " + p2);
    }
}
