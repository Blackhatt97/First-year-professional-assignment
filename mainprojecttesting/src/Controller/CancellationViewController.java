package Controller;

import Model.Contract;
import Model.DBWrapper.DBConn;
import Model.PriceCalculator;
import Model.Reservation;
import com.sun.xml.internal.stream.util.ThreadLocalBufferAllocator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by CIA on 28/05/2017.
 */
public class CancellationViewController {

    @FXML private Button cancelReservation;
    @FXML private TextArea cancelText;
    private int reservationID;
    private double initPrice;

    @FXML public void initialize(){

        Thread one = new Thread(() -> {
            try {
                Thread.sleep(10);
                writeCancellationText();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        one.start();

    }



    public void setInitPrice(double initPrice) {
        this.initPrice = initPrice;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    private void writeCancellationText(){

        DBConn dbConn = new DBConn();
        boolean isCancelled = dbConn.isReservationCancelled(reservationID);
        if (isCancelled){
            cancelText.setText("THIS RESERVATION IS ALREADY CANCELLED");
            cancelReservation.setDisable(true);
        }
        dbConn = null;
        Contract contract = new Contract();
        cancelText.setText(cancelText.getText() + "\n" + contract.createCancellationText(reservationID, initPrice));

    }

    public void cancelReservation(ActionEvent actionEvent) {

        DBConn dbConn = new DBConn();
        dbConn.cancelReservation(reservationID);
        writeCancellationText();
        dbConn = null;

    }
}
