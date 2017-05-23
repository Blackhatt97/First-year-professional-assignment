package Controller;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by blackhatt on 16/05/2017.
 */
public class ReservationViewController {

    @FXML private DatePicker reservationRange;
    @FXML private DatePicker check_in;
    @FXML DatePicker reservationPicker;
    @FXML ChoiceBox mhTypeCheck;
    @FXML TableView mhTableView;

    private TypeData typeData = new TypeData();
    private MotorhomeData motorhomeData = new MotorhomeData();

    ArrayList<LocalDate> range = new ArrayList<>();
    DateChecker dateChecker = new DateChecker();
    ArrayList<Pair<LocalDate, LocalDate>> dateRanges = new ArrayList<>();
    @FXML
    public void initialize() {

        mhTypeCheck.getItems().setAll(typeData.getData());

        mhTypeCheck.getSelectionModel()
                .selectedItemProperty()
                .addListener(new ChangeListener<Pair<Integer, String>>() {
            @Override
            public void changed(ObservableValue<? extends Pair<Integer, String>> observable,
                                Pair<Integer, String> oldValue, Pair<Integer, String> newValue) {
                if (mhTypeCheck.getSelectionModel().getSelectedItem() != null) {
                    motorhomeData.loadList(newValue.getKey());
                    mhTableView.setItems(motorhomeData.getMotorhomeList());
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////
        //add css coloring for the datepicker between the ranges
//        check_in.setValue(LocalDate.now());
//        reservationRange.setValue(check_in.getValue().plusDays(10));
//        DateChecker.setBeginDateBounds(check_in, reservationRange.getValue());
//        DateChecker.setEndDateBounds(reservationRange, check_in.getValue());

//        check_in.setOnAction((event) -> {
//
//            dateChecker.setEndDateBounds(reservationRange, check_in.getValue());
//            LocalDate startDate = check_in.getValue();
//            System.out.println("Start Date: " + startDate.toString());
//
//        });
        LocalDate localDateStart = LocalDate.of(2017, 5, 25);
        LocalDate localDateEnd = LocalDate.of(2017, 5, 29);
        //dateChecker.setDisabledRange(reservationRange, localDateStart, localDateEnd);

        LocalDate localDateStart1 = LocalDate.of(2017, 6, 5);
        LocalDate localDateEnd1 = LocalDate.of(2017, 6, 10);
        //dateChecker.setDisabledRange(reservationRange, localDateStart1, localDateEnd1);

        Pair<LocalDate, LocalDate> localDatePair = new Pair<>(localDateStart, localDateEnd);
        Pair<LocalDate, LocalDate> localDatePair1 = new Pair<>(localDateStart1, localDateEnd1);


        dateRanges.add(localDatePair);
        dateRanges.add(localDatePair1);

        dateChecker.setDisabledRange(reservationRange, dateRanges);


//        reservationRange.setOnAction((event) -> {
//
//            LocalDate endDate = reservationRange.getValue();
//            System.out.println("End Date: " + endDate.toString());
//
//        });
    }

    public void chooseDate(MouseEvent mouseEvent) {

    }

    public void calcPeriod(){

    }
}
