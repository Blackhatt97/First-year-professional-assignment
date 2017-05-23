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

    public void setDisabledRange(DatePicker datePicker, ArrayList<Pair<LocalDate, LocalDate>> pairArrayList){
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {

                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        for (int i = 0; i < pairArrayList.size() ; i++) {

                            super.updateItem(item, empty);
                            boolean cond = (item.isBefore(pairArrayList.get(i).getValue()) && item.isAfter(pairArrayList.get(i).getKey()));
                            if (cond) {
                                setDisable(true);
                                setStyle("-fx-background-color: #d3d3d3;");
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

    public void setDisableAfterDate(DatePicker datePicker, LocalDate date){
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {

                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean cond = (!item.isAfter(date));
                        if (cond){
                            setDisable(true);
                            setStyle("-fx-background-color: #d3d3d3;");
                        }else{
                            setDisable(false);
                            setStyle("-fx-background-color: #CCFFFF;");
                            setStyle("-fx-font-fill: black;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }
}