package Controller;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

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
    @FXML ChoiceBox mhTypeCheck;
    @FXML TableView mhTableView;

    private TypeData typeData = new TypeData();
    private MotorhomeData motorhomeData = new MotorhomeData();

    ArrayList<LocalDate> range = new ArrayList<>();
    boolean dateButtonClicked = false;
    int dateButtonClicks = 0;
    DateChecker dateChecker = new DateChecker();

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
//        check_out.setValue(check_in.getValue().plusDays(10));
//        DateChecker.setBeginDateBounds(check_in, check_out.getValue());
//        DateChecker.setEndDateBounds(check_out, check_in.getValue());
        check_in.setOnAction((event) -> {

            dateChecker.setEndDateBounds(check_out, check_in.getValue());
            LocalDate startDate = check_in.getValue();
            System.out.println("Start Date: " + startDate.toString());

        });

        check_out.setOnAction((event) -> {

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
