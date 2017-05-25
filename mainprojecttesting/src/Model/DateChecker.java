package Model;

/**
 * Created by CIA on 22/05/2017.
 */
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.Pair;

public class DateChecker {

    public DateChecker(){
    }

    public LocalDate findClosestReservationDate(ArrayList<Pair<LocalDate, LocalDate>> datePairs, LocalDate startDate) {

        ArrayList<LocalDate> allStartDatesAfterStartDate = new ArrayList<>();
        for (int i = 0; i < datePairs.size() ; i++) {
            if (datePairs.get(i).getKey().isAfter(startDate)){
                allStartDatesAfterStartDate.add(datePairs.get(i).getKey());
            }
        }
        LocalDate closestDate = LocalDate.of(5000, 12, 1);
        for (int i = 0; i < allStartDatesAfterStartDate.size() ; i++) {
            if (allStartDatesAfterStartDate.get(i).isBefore(closestDate)){
                closestDate = allStartDatesAfterStartDate.get(i);
            }
        }

        return closestDate;
    }

    public void setDisabledRange(DatePicker datePicker, ArrayList<Pair<LocalDate, LocalDate>> pairArrayList, boolean disableBeforeToday){
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {

                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        for (int i = 0; i < pairArrayList.size() ; i++) {

                            super.updateItem(item, empty);
                            boolean cond = (item.isBefore(pairArrayList.get(i).getValue()) && item.isAfter(pairArrayList.get(i).getKey()));
                            if (item.isBefore(pairArrayList.get(i).getValue()) && item.isAfter(pairArrayList.get(i).getKey())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #d3d3d3;");
                            }
                            if (item.isEqual(pairArrayList.get(i).getKey())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #d3d3d3;");
                            }
                            if (item.isEqual(pairArrayList.get(i).getValue())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #d3d3d3;");
                            }
                            if (disableBeforeToday){
                                if (item.isBefore(LocalDate.now())){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #d3d3d3;");
                                }
                            }
//                            else {
//                                setDisable(false);
//                                setStyle("-fx-background-color: #CCFFFF;");
//                                setStyle("-fx-font-fill: black;");
//
//                            }

                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    public void setDisabledRangeWithHighlightedCurrentRange(DatePicker datePicker,
                                                            ArrayList<Pair<LocalDate, LocalDate>> pairArrayList,
                                                            boolean disableBeforeToday,
                                                            Pair<LocalDate, LocalDate> currentRange){
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {

                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        for (int i = 0; i < pairArrayList.size() ; i++) {

                            super.updateItem(item, empty);
                            boolean cond = (item.isBefore(pairArrayList.get(i).getValue()) && item.isAfter(pairArrayList.get(i).getKey()));
                            if (item.isBefore(pairArrayList.get(i).getValue()) && item.isAfter(pairArrayList.get(i).getKey())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #d3d3d3;");
                                if (pairArrayList.get(i).getValue().isEqual(currentRange.getValue()) && pairArrayList.get(i).getKey().isEqual(currentRange.getKey())){
                                    setStyle("-fx-background-color: red;");
                                }
                            }
                            if (item.isEqual(pairArrayList.get(i).getKey())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #d3d3d3;");
                                if (pairArrayList.get(i).getValue().isEqual(currentRange.getValue()) && pairArrayList.get(i).getKey().isEqual(currentRange.getKey())){
                                    setStyle("-fx-background-color: red;");
                                }
                            }
                            if (item.isEqual(pairArrayList.get(i).getValue())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #d3d3d3;");
                                if (pairArrayList.get(i).getValue().isEqual(currentRange.getValue()) && pairArrayList.get(i).getKey().isEqual(currentRange.getKey())){
                                    setStyle("-fx-background-color: red;");
                                }
                            }
                            if (disableBeforeToday){
                                if (item.isBefore(LocalDate.now())){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #d3d3d3;");
                                }
                            }
//                            else {
//                                setDisable(false);
//                                setStyle("-fx-background-color: #CCFFFF;");
//                                setStyle("-fx-font-fill: black;");
//
//                            }

                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }
    @SuppressWarnings("Duplicates")
    public void setDisableAfterAndBeforeRange(DatePicker datePicker, LocalDate startDate, LocalDate endDate){
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {

                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean cond = (item.isAfter(endDate));
                        if (item.isAfter(endDate)){
                            setDisable(true);
                            setStyle("-fx-background-color: #d3d3d3;");
                        }
                        if (item.isBefore(startDate)){
                            setDisable(true);
                            setStyle("-fx-background-color: #d3d3d3;");
                        }
//                        else{
//                            setDisable(false);
//                            setStyle("-fx-background-color: #CCFFFF;");
//                            setStyle("-fx-font-fill: black;");
//                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    public void setDisableAfterDate(DatePicker datePicker, LocalDate date){
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {

                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean cond = (item.isAfter(date));
                        if (cond){
                            setDisable(true);
                            setStyle("-fx-background-color: #d3d3d3;");
                        }
//                        else{
//                            setDisable(false);
//                            setStyle("-fx-background-color: #CCFFFF;");
//                            setStyle("-fx-font-fill: black;");
//                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    public void setDisableBeforeDate(DatePicker datePicker, LocalDate date){
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {

                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean cond = (item.isBefore(date));
                        if (cond){
                            setDisable(true);
                            setStyle("-fx-background-color: #d3d3d3;");
                        }
//                      else{
//                            setDisable(false);
//                            setStyle("-fx-background-color: #CCFFFF;");
//                            setStyle("-fx-font-fill: black;");
//                      }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }
}