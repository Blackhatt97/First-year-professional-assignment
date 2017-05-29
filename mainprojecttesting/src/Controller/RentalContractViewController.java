package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by CIA on 29/05/2017.
 */
public class RentalContractViewController {

    @FXML private TextArea rentalText;
    int rentalID;
    double totalPrice;

    @FXML public void initialize(){

        Thread one = new Thread(() -> {
            try {
                Thread.sleep(10);
                rentalText.setText(String.valueOf(rentalID) + " " + String.valueOf(totalPrice));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        one.start();

    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private void writeRentalContractText(){



    }

    public void createRental(ActionEvent actionEvent) {


    }
}
