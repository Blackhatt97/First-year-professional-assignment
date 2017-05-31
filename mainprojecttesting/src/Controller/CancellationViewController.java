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

    //this method calls upon writeCancellationText, it is done on another thread because we are passing parameters
    //from another controller to this one and this happens after the controller is initialized.
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

    //this method writes a fresh cancellation text if the reservation hasn't already been cancelled
    //and fetches an existing text if the cancellation is already cancelled
    private void writeCancellationText(){

        DBConn dbConn = new DBConn();
        boolean isCancelled = dbConn.isReservationCancelled(reservationID);
        if (!isCancelled){

            Contract contract = new Contract();
            cancelText.setText(cancelText.getText() + "\n" + contract.createCancellationText(reservationID, initPrice));

        }

        else {

            cancelReservation.setDisable(true);
            cancelText.setText("THIS RESERVATION IS ALREADY CANCELLED");
            cancelText.setText(cancelText.getText() + dbConn.getCancelledReservationText(reservationID));

        }
        dbConn = null;

    }

    //this method is called upon when the cancel button is pressed, this cancels the reservation in the database (boolean)
    //and adds the cancellation text to the DB
    public void cancelReservation(ActionEvent actionEvent) {

        DBConn dbConn = new DBConn();
        dbConn.cancelReservation(reservationID);
        dbConn.addCancelledReservationInvoiceToDB(reservationID, cancelText.getText());
        writeCancellationText();
        dbConn =  null;

    }
}
