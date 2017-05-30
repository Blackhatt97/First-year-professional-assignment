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

    @FXML TextField reservationIDField;
    @FXML TableView<Reservation> reservationTable;
    @FXML DatePicker reservationDateEnd;
    @FXML DatePicker reservationDateBegin;
    @FXML ChoiceBox<String> seasonChoiceBox;
    @FXML TextField priceField;
    @FXML DatePicker reservationPicker;
    @FXML ChoiceBox<Pair<Integer, String>> mhTypeCheck;
    @FXML TableView<Motorhome> mhTableView;
    @FXML ComboBox<Customer> customerBox;
    @FXML TextField searchField;
    @FXML ToggleButton toggleCancelled;

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

    private void loadAllReservations() {
        boolean loadCancelled = false;
        if (toggleCancelled.isSelected()) {
            loadCancelled = true;
        }
        reservationData.loadList(loadCancelled);
        reservationTable.setItems(reservationData.getReservationList());
    }

    @FXML
    public void createReservation(ActionEvent actionEvent) {
        resetBorders();
        if (checkErrors() == 0) {
            DBConn dbConn = new DBConn();
            boolean reservationExists = false;
            if (!reservationIDField.getText().isEmpty() && checkInteger(reservationIDField) == 0) {
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
    }

    private int checkInteger(TextField field) {
        try {
            Integer.parseInt(field.getText());
        } catch (NumberFormatException ex) {
            field.setStyle("-fx-border-color: red;");
            return 1;
        }
        return 0;
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
        priceField.setText("");
    }

    @FXML
    public void updateReservation(ActionEvent actionEvent) {
        resetBorders();
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if (reservation != null && checkErrors() == 0) {
            DBConn dbConn = new DBConn();
            Customer customer = customerBox.getSelectionModel().getSelectedItem();
            dbConn.updateReservation(
                    reservation.getId(),
                    customer.getId(),
                    java.sql.Date.valueOf(reservationPicker.getValue()),
                    java.sql.Date.valueOf(reservationDateBegin.getValue()),
                    java.sql.Date.valueOf(reservationDateEnd.getValue()),
                    0,
                    0,
                    mhTableView.getSelectionModel().getSelectedItem().getId(),
                    seasonChoiceBox.getValue()
            );
            loadAllReservations();
        }
    }

    @FXML
    public void loadAll(ActionEvent e) {
        loadAllReservations();
    }

    @FXML
    public void rent(ActionEvent e) {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        DBConn dbConn = new DBConn();
        if (reservation != null && dbConn.checkIfReservationExists(reservation.getId())) {
            if (!dbConn.isReservationCancelled(reservation.getId())) {
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
        resetBorders();
        loadAllReservations();
    }

    private void reservationCancel() {
        Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if (reservation != null && checkDouble(priceField) == 0) {
            try {
                FXMLLoader root = new FXMLLoader(getClass().getResource("/View/CancellationView.fxml"));
                Scene scene = new Scene(root.load());
                ((CancellationViewController) root.getController())
                        .setReservationID(reservation.getId());
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

    private int checkDouble(TextField field) {
        try {
            Double.parseDouble(field.getText());
        } catch (NumberFormatException ex) {
            field.setStyle("-fx-border-color: red;");
            return 1;
        }
        return 0;
    }

    @FXML
    public void cancelToggled(ActionEvent actionEvent) {
        resetFields();
        resetBorders();
        loadAllReservations();
    }

    private void resetBorders() {
        customerBox.setStyle("-fx-border-color: transparent");
        reservationPicker.setStyle("-fx-border-color: transparent");
        reservationDateBegin.setStyle("-fx-border-color: transparent");
        reservationDateEnd.setStyle("-fx-border-color: transparent");
        mhTableView.setStyle("-fx-border-color: transparent");
        reservationIDField.setStyle("-fx-border-color: transparent");
        priceField.setStyle("-fx-border-color: transparent");
    }

    private int checkErrors() {
        int counter = 0;
        if (customerBox.getSelectionModel().isEmpty()) {
            customerBox.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (reservationPicker.getEditor().getText().isEmpty()) {
            reservationPicker.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (reservationDateBegin.getEditor().getText().isEmpty()) {
            reservationDateBegin.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (reservationDateEnd.getEditor().getText().isEmpty()) {
            reservationDateEnd.setStyle("-fx-border-color: red;");
            counter++;
        }
        if (mhTableView.getSelectionModel().getSelectedItem() != null) {
            mhTableView.setStyle("-fx-border-color: red;");
            counter++;
        }
        return counter;
    }
}