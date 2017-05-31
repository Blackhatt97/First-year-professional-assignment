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

    //this method calls upon writeRentalContract, it is done on another thread because we are passing parameters
    //from another controller to this one and this happens after the controller is initialized.
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

    //this method writes a fresh rental contract text if a rental contract hasn't already been created
    //and fetches an existing text if the rental contract is already created
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

    //this method is called upon when the rent button is pressed, this creates a rental contract in the database (boolean)
    //and adds the rental text to the DB
    public void createRental(ActionEvent actionEvent) {

        DBConn dbConn = new DBConn();
        dbConn.createRentalContract(rentalID);
        dbConn.addCreatedRentalContractTextToDB(rentalID, rentalText.getText());
        writeRentalContractText();
        dbConn =  null;

    }
}
