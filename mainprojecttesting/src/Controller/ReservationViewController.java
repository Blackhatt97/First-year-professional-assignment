package Controller;

import Model.DateChecker;
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

    @FXML private DatePicker check_out;
    @FXML private DatePicker check_in;
    @FXML DatePicker reservationPicker;

    ArrayList<LocalDate> range = new ArrayList<>();
    boolean dateButtonClicked = false;
    int dateButtonClicks = 0;
    DateChecker dateChecker = new DateChecker();

    @FXML public void initialize(){
        //add css coloring for the datepicker between the ranges
//        check_in.setValue(LocalDate.now());
//        check_out.setValue(check_in.getValue().plusDays(10));
//        DateChecker.setBeginDateBounds(check_in, check_out.getValue());
//        DateChecker.setEndDateBounds(check_out, check_in.getValue());
        check_in.setOnAction( (event) ->   {

            dateChecker.setEndDateBounds(check_out, check_in.getValue());
            LocalDate startDate = check_in.getValue();
            System.out.println("Start Date: " + startDate.toString());

        });

        check_out.setOnAction( (event) -> {

            dateChecker.setBeginDateBounds(check_in, check_out.getValue());
            LocalDate endDate = check_out.getValue();
            System.out.println("End Date: " + endDate.toString());

        });

    }

    public void chooseDate(MouseEvent mouseEvent) {

    }

    public void calcPeriod(){

    }
}
