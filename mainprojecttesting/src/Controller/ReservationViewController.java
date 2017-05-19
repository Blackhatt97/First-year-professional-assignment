package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by blackhatt on 16/05/2017.
 */
public class ReservationViewController {

    @FXML DatePicker reservationPicker;
    ArrayList<LocalDate> range = new ArrayList<>();
    boolean dateButtonClicked = false;
    @FXML public void initialize(){

        reservationPicker.setOnAction(event -> {
            if (dateButtonClicked){
                dateButtonClicked = false;
            }
            LocalDate date = reservationPicker.getValue();
            System.out.println("Selected date: " + date);

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
}
