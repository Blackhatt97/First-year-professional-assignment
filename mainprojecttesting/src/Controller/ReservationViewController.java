package Controller;

import Model.*;
import Model.DBWrapper.DBConn;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by blackhatt on 16/05/2017.
 */
public class ReservationViewController {

    @FXML private TextField reservationIDField;
    @FXML private TableView reservationTable;
    @FXML private DatePicker reservationDateEnd;
    @FXML private DatePicker reservationDateBegin;
    @FXML DatePicker reservationPicker;
    @FXML ChoiceBox<Pair<Integer, String>> mhTypeCheck;
    @FXML TableView<Motorhome> mhTableView;
    @FXML ComboBox<Customer> customerBox;
    @FXML TextField searchField;

    private TypeData typeData = new TypeData();
    private MotorhomeData motorhomeData = new MotorhomeData();
    private CustomerData customerData = new CustomerData();
    private ReservationData reservationData = new ReservationData();

    ArrayList<LocalDate> range = new ArrayList<>();
    DateChecker dateChecker = new DateChecker();
    ArrayList<Pair<LocalDate, LocalDate>> dateRanges = new ArrayList<>();
    @FXML
    public void initialize() {

        //loading all reservations and adding listener to reservation table
        loadAllReservations();
        reservationTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Reservation>() {
            @Override
            public void changed(ObservableValue<? extends Reservation> observable, Reservation oldValue, Reservation newValue) {
                if (reservationTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = reservationTable.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                    updateFields((Reservation) selectedItem);
                }
            }
        });

        //listener for motorhome table
        mhTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Motorhome>() {
            @Override
            public void changed(ObservableValue<? extends Motorhome> observable, Motorhome oldValue, Motorhome newValue) {
                if (mhTableView.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = mhTableView.getSelectionModel();
                    Object selectedItem = selectionModel.getSelectedItem();
                    updateDatePickersWithMotorhome((Motorhome) selectedItem);
                }
            }
        });

        //defining customers inside customer choice box
        customerData.loadList();
        customerBox.visibleRowCountProperty()
                .set(customerData.getCustomerList().size() >= 10 ? 10 : customerData.getCustomerList().size());
        customerBox.setItems(customerData.getCustomerList());

        //searching by type in motorhome table
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

        //searching for customers inside choice box by string values in text field
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customerBox.getSelectionModel().select(null);
                if (!customerBox.isShowing()) {
                    customerBox.show();
                }
                if (!newValue.isEmpty()) {
                    ObservableList<Customer> data = customerData.getSearchedList(newValue);
                    updateCustomerBox(data);
                } else {
                    ObservableList<Customer> data = customerData.getCustomerList();
                    updateCustomerBox(data);
                }
            }
        });

        //Unfinished date business, still early experimental
        reservationPicker.setValue(LocalDate.now());
        reservationDateBegin.setOnAction((event) -> {
            if (reservationDateBegin.getValue() != null) {
                LocalDate disableAfterClosestReservation = dateChecker.findClosestReservationDate(dateRanges, reservationDateBegin.getValue());
                dateChecker.setDisableAfterAndBeforeRange(reservationDateEnd, reservationDateBegin.getValue(), disableAfterClosestReservation);
                LocalDate startDate = reservationDateBegin.getValue();
                System.out.println("Start Date: " + startDate.toString());
            }

        });

        reservationDateEnd.setOnAction((event) -> {
            if (reservationDateEnd.getValue() != null) {
                LocalDate endDate = reservationDateEnd.getValue();
                System.out.println("End Date: " + endDate.toString());
            }

        });

//        LocalDate localDateStart = LocalDate.of(2017, 5, 25);
//        LocalDate localDateEnd = LocalDate.of(2017, 5, 25);
//
//        LocalDate localDateStart1 = LocalDate.of(2017, 6, 5);
//        LocalDate localDateEnd1 = LocalDate.of(2017, 6, 10);
//
//        Pair<LocalDate, LocalDate> localDatePair = new Pair<>(localDateStart, localDateEnd);
//        Pair<LocalDate, LocalDate> localDatePair1 = new Pair<>(localDateStart1, localDateEnd1);
//
//        dateRanges.add(localDatePair);
//        dateRanges.add(localDatePair1);
//
//        dateChecker.setDisabledRange(reservationDateEnd, dateRanges, true);
//        dateChecker.setDisabledRange(reservationDateBegin, dateRanges, true);

    }

    private void updateCustomerBox(ObservableList<Customer> data) {
        customerBox.setItems(data);
        customerBox.hide();
        customerBox.visibleRowCountProperty().set(data.size() >= 10 ? 10 : data.size());
        customerBox.show();
    }

    private void updateFields(Reservation reservation) {

        reservationIDField.setText(String.valueOf(reservation.getId()));

        customerBox.setItems(customerData.getCustomerList());
        customerBox.getSelectionModel().select(customerData.searchById(reservation.getCustId()));

        reservationPicker.setValue(reservation.getReservationDate().toLocalDate());
        DBConn dbConn = new DBConn();
        mhTypeCheck.getSelectionModel()
                .select(typeData.searchById(dbConn.getMotorhomeType(reservation.getMotorhomeId())));

        Motorhome motorhome = motorhomeData.searchById(reservation.getMotorhomeId());
        mhTableView.getSelectionModel().select(motorhome);
        mhTableView.scrollTo(motorhome);

        //get all reservations begin and end dates as periods for the selected motorhome
        dateRanges.clear();
        dateRanges = dbConn.getAllReservationDatesForMotorhome(reservation.getMotorhomeId());

        //add them to date ranges
        //then disable those ranges in the datepickers
        Pair<LocalDate, LocalDate> currentRange = new Pair<LocalDate, LocalDate>(reservation.getStartDate().toLocalDate(), reservation.getEndDate().toLocalDate());
        dateChecker.setDisabledRangeWithHighlightedCurrentRange(reservationDateEnd, dateRanges, true, currentRange);
        dateChecker.setDisabledRangeWithHighlightedCurrentRange(reservationDateBegin, dateRanges, true, currentRange);
        //change the reservation begin and end fields to all of the reservations that the selected motorhome has?
        reservationDateBegin.setValue(reservation.getStartDate().toLocalDate());
        reservationDateEnd.setValue(reservation.getEndDate().toLocalDate());
        LocalDate disableAfterClosestReservation = dateChecker.findClosestReservationDate(dateRanges, reservationDateBegin.getValue());
        dateChecker.setDisableAfterAndBeforeRangeWithHighlight(reservationDateEnd, reservationDateBegin.getValue(), disableAfterClosestReservation, currentRange);
        dbConn = null;

    }

    private void updateDatePickersWithMotorhome(Motorhome motorhome){

            DBConn dbConn = new DBConn();
            dateRanges.clear();
            dateRanges = dbConn.getAllReservationDatesForMotorhome(motorhome.getId());
            dateChecker.setDisabledRange(reservationDateEnd, dateRanges, true);
            dateChecker.setDisabledRange(reservationDateBegin, dateRanges, true);


            dbConn = null;

    }

    public void loadAllReservations(){

        reservationData.loadList();
        reservationTable.setItems(reservationData.getReservationList());


    }

    public void createReservation(ActionEvent actionEvent) {

        DBConn dbConn = new DBConn();
        boolean reservationExists = false;
        if (!reservationIDField.getText().equals("")){
            reservationExists = dbConn.checkIfReservationExists(Integer.parseInt(reservationIDField.getText()));
        }
        if (!reservationExists) {
            Customer customer = customerBox.getSelectionModel().getSelectedItem();
            dbConn.addReservationToDB(customer.getId(),
                    java.sql.Date.valueOf(reservationPicker.getValue()),
                    java.sql.Date.valueOf(reservationDateBegin.getValue()),
                    java.sql.Date.valueOf(reservationDateEnd.getValue()),
                    0,
                    0,
                    mhTableView.getSelectionModel().getSelectedItem().getId());
            loadAllReservations();
        }

        else {

            System.out.println("Reservation already exists, use the update button.");

        }
        dbConn = null;
        resetFields();

    }

    public void resetAll(ActionEvent actionEvent) {

        resetFields();

    }

    private void resetFields(){
        reservationIDField.setText("");
        customerBox.setValue(null);
        reservationPicker.setValue(LocalDate.now());
        reservationDateBegin.setValue(null);
        reservationDateEnd.setValue(null);
        mhTypeCheck.setValue(null);
        mhTableView.getItems().clear();
        reservationTable.getSelectionModel().clearSelection();
    }
}
