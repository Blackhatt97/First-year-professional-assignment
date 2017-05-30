package Controller;

import Model.*;
import Model.DBWrapper.DBConn;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationViewController {

    @FXML
    private TextField reservationIDField;
    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private DatePicker reservationDateEnd;
    @FXML
    private DatePicker reservationDateBegin;
    @FXML
    private ChoiceBox<String> seasonChoiceBox;
    @FXML
    private TextField priceField;
    @FXML
    DatePicker reservationPicker;
    @FXML
    ChoiceBox<Pair<Integer, String>> mhTypeCheck;
    @FXML
    TableView<Motorhome> mhTableView;
    @FXML
    ComboBox<Customer> customerBox;
    @FXML
    TextField searchField;
    @FXML
    ToggleButton toggleCancelled;

    private TypeData typeData = new TypeData();
    private MotorhomeData motorhomeData = new MotorhomeData();
    private CustomerData customerData = new CustomerData();
    private ReservationData reservationData = new ReservationData();

    private DateChecker dateChecker = new DateChecker();
    private ArrayList<Pair<LocalDate, LocalDate>> dateRanges = new ArrayList<>();

    @FXML
    public void initialize() {

        //populate seasons choicebox
        seasonChoiceBox.getItems().addAll(reservationData.getSeasons());
        seasonChoiceBox.setValue("Low");

        //loading all reservations and adding listener to reservation table
        loadAllReservations();
        reservationTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Reservation>() {
            @Override
            public void changed(ObservableValue<? extends Reservation> observable,
                                Reservation oldValue, Reservation newValue) {
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
            public void changed(ObservableValue<? extends Motorhome> observable,
                                Motorhome oldValue, Motorhome newValue) {
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

        reservationPicker.setValue(LocalDate.now());
        reservationDateBegin.setOnAction((event) -> {
            if (reservationDateBegin.getValue() != null) {
                LocalDate disableAfterClosestReservation =
                        dateChecker.findClosestReservationDateAfter(dateRanges, reservationDateBegin.getValue());
                dateChecker.setDisableAfterAndBeforeRange(reservationDateEnd,
                        reservationDateBegin.getValue(), disableAfterClosestReservation);
            }
        });

        reservationDateEnd.setOnAction((event) -> {
            if (reservationDateEnd.getValue() != null) {
                LocalDate disableBeforeClosestReservation =
                        dateChecker.findClosestReservationDateBefore(dateRanges, reservationDateEnd.getValue());
                dateChecker.setDisableAfterAndBeforeRange(reservationDateBegin,
                        disableBeforeClosestReservation, reservationDateEnd.getValue());

                PriceCalculator calculator = new PriceCalculator();
                priceField.setText(String.valueOf(calculator.getPrice(reservationDateBegin.getValue(),
                        reservationDateEnd.getValue(),
                        seasonChoiceBox.getValue(),
                        mhTypeCheck.getValue().getKey())));
            }
        });
    }

    private void updateCustomerBox(ObservableList<Customer> data) {
        customerBox.setItems(data);
        customerBox.hide();
        customerBox.visibleRowCountProperty().set(data.size() >= 10 ? 10 : data.size());
        customerBox.show();
    }

    private void updateFields(Reservation reservation) {

        seasonChoiceBox.setValue(reservation.getSeason());
        reservationIDField.setText(String.valueOf(reservation.getId()));

        customerBox.setItems(customerData.getCustomerList());
        customerBox.getSelectionModel().select(customerData.searchById(reservation.getCustId()));

        reservationPicker.setValue(reservation.getReservationDate().toLocalDate());
        DBConn dbConn = new DBConn();
        int motorhomeType = dbConn.getMotorhomeType(reservation.getMotorhomeId());
        mhTypeCheck.getSelectionModel()
                .select(typeData.searchById(motorhomeType));

        Motorhome motorhome = motorhomeData.searchById(reservation.getMotorhomeId());
        mhTableView.getSelectionModel().select(motorhome);
        mhTableView.scrollTo(motorhome);

        //get all reservations begin and end dates as periods for the selected motorhome
        dateRanges.clear();
        dateRanges = dbConn.getAllReservationAndRentalDatesForMotorhome(reservation.getMotorhomeId());

        //add them to date ranges
        //then disable those ranges in the datepickers
        Pair<LocalDate, LocalDate> currentRange = new Pair<LocalDate, LocalDate>(reservation.getStartDate().toLocalDate(),
                reservation.getEndDate().toLocalDate());
        dateChecker.setDisabledRangeWithHighlightedCurrentRange(reservationDateEnd,
                dateRanges, true, currentRange);
        dateChecker.setDisabledRangeWithHighlightedCurrentRange(reservationDateBegin,
                dateRanges, true, currentRange);
        //change the reservation begin and end fields to all of the reservations that the selected motorhome has?
        reservationDateBegin.setValue(reservation.getStartDate().toLocalDate());
        reservationDateEnd.setValue(reservation.getEndDate().toLocalDate());

        LocalDate disableAfterClosestReservation =
                dateChecker.findClosestReservationDateAfter(dateRanges, reservationDateBegin.getValue());
        dateChecker.setDisableAfterAndBeforeRangeWithHighlight(reservationDateEnd,
                reservationDateBegin.getValue(), disableAfterClosestReservation, currentRange);

        LocalDate disableBeforeClosestReservation =
                dateChecker.findClosestReservationDateBefore(dateRanges, reservationDateEnd.getValue());
        dateChecker.setDisableAfterAndBeforeRangeWithHighlight(reservationDateBegin,
                disableBeforeClosestReservation, reservationDateEnd.getValue(), currentRange);

        PriceCalculator calculator = new PriceCalculator();
        priceField.setText(String.valueOf(calculator.getPrice(reservationDateBegin.getValue(),
                reservationDateEnd.getValue(),
                reservation.getSeason(),
                motorhomeType)));
    }

    private void updateDatePickersWithMotorhome(Motorhome motorhome) {
        DBConn dbConn = new DBConn();
        dateRanges.clear();
        dateRanges = dbConn.getAllReservationAndRentalDatesForMotorhome(motorhome.getId());
        dateChecker.setDisabledRange(reservationDateEnd, dateRanges, true);
        dateChecker.setDisabledRange(reservationDateBegin, dateRanges, true);
    }

    public void loadAllReservations() {
        boolean loadCancelled = false;
        if (toggleCancelled.isSelected()) {
            loadCancelled = true;
        }
        reservationData.loadList(loadCancelled);
        reservationTable.setItems(reservationData.getReservationList());
    }

    @FXML
    public void createReservation(ActionEvent actionEvent) {
        DBConn dbConn = new DBConn();
        boolean reservationExists = false;
        if (!reservationIDField.getText().isEmpty()) {
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
                    mhTableView.getSelectionModel().getSelectedItem().getId(),
                    seasonChoiceBox.getValue());
            loadAllReservations();
            resetFields();
        }
    }

    @FXML
    public void resetAll(ActionEvent actionEvent) {
        resetFields();
    }

    private void resetFields() {

        reservationIDField.setText("");
        customerBox.setValue(null);
        reservationPicker.setValue(LocalDate.now());
        reservationDateBegin.setValue(null);
        reservationDateEnd.setValue(null);
        mhTypeCheck.setValue(null);
        mhTableView.getItems().clear();
        reservationTable.getSelectionModel().clearSelection();
        //seasonChoiceBox.getSelectionModel().select(null);
        priceField.setText("");

    }

    @FXML
    public void updateReservation(ActionEvent actionEvent) {
        DBConn dbConn = new DBConn();
        Customer customer = customerBox.getSelectionModel().getSelectedItem();
        int currentReservationID = Integer.parseInt(reservationIDField.getText());
        dbConn.updateReservation(Integer.parseInt(reservationIDField.getText()),
                customer.getId(),
                java.sql.Date.valueOf(reservationPicker.getValue()),
                java.sql.Date.valueOf(reservationDateBegin.getValue()),
                java.sql.Date.valueOf(reservationDateEnd.getValue()),
                0,
                0,
                mhTableView.getSelectionModel().getSelectedItem().getId(),
                seasonChoiceBox.getValue());
        loadAllReservations();
        Reservation reservation = dbConn.getReservationFromDB(currentReservationID);
        updateFields(reservation);
    }

    @FXML
    public void loadAll(ActionEvent e) {
        loadAllReservations();
    }

    @FXML
    public void rent(ActionEvent e) {
        DBConn dbConn = new DBConn();
        if (!reservationIDField.getText().isEmpty() &&
                dbConn.checkIfReservationExists(Integer.parseInt(reservationIDField.getText()))) {
            if (!dbConn.isReservationCancelled(Integer.parseInt(reservationIDField.getText()))) {
                Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
                dbConn.addRentalToDB(reservation);
                dbConn.deleteFromDB(reservation.getId(), "reservations");
                loadAllReservations();
                resetFields();
            } else {
                reservationCancel();
            }
        }
    }

    @FXML
    public void cancelReservation(ActionEvent actionEvent) {
        reservationCancel();
        resetFields();
        loadAllReservations();
    }

    private void reservationCancel() {
        if (reservationTable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader root = new FXMLLoader(getClass().getResource("/View/CancellationView.fxml"));
                Scene scene = new Scene(root.load());
                ((CancellationViewController) root.getController())
                        .setReservationID(Integer.parseInt(reservationIDField.getText()));
                ((CancellationViewController) root.getController())
                        .setInitPrice(Double.valueOf(priceField.getText()));
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void cancelToggled(ActionEvent actionEvent) {
        resetFields();
        loadAllReservations();
    }
}