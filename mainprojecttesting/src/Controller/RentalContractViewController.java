package Controller;

import Model.Contract;
import Model.DBWrapper.DBConn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Created by CIA on 29/05/2017.
 */
public class RentalContractViewController {

    @FXML private Button rentalContract;
    @FXML private TextArea rentalText;
    int rentalID;
    double totalPrice;

    @FXML public void initialize(){

        Thread one = new Thread(() -> {
            try {
                Thread.sleep(10);
                writeRentalContractText();
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

        DBConn dbConn = new DBConn();
        boolean isCreated = dbConn.isRentalContractCreated(rentalID);
        if (!isCreated){

            Contract contract = new Contract();
            rentalText.setText(rentalText.getText() + "\n" + contract.createRentalText(rentalID, totalPrice));

        }

        else {

            rentalContract.setDisable(true);
            rentalText.setText("THIS RENTAL CONTRACT IS ALREADY CREATED");
            rentalText.setText(rentalText.getText() + dbConn.getRentalContractText(rentalID));

        }
        dbConn = null;

    }

    public void createRental(ActionEvent actionEvent) {

        DBConn dbConn = new DBConn();
        dbConn.createRentalContract(rentalID);
        dbConn.addCreatedRentalContractTextToDB(rentalID, rentalText.getText());
        writeRentalContractText();
        dbConn =  null;

    }
}
