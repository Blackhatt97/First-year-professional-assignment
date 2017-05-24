package Model;

import Model.DBWrapper.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by blackhatt on 17/05/2017.
 */
public class CustomerData {

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    public ObservableList<Customer> getCustomerList() {
        return customerList;
    }

    public void loadList() {
        DBConn dbConn = new DBConn();
        customerList = dbConn.getAllCustomers();
    }

    public ObservableList<Customer> getSearchedList(String search) {
        ObservableList<Customer> tempList = FXCollections.observableArrayList();
        for (Customer customer : customerList) {
            if (customer.match(search)) {
                tempList.add(customer);
            }
        }
        return tempList;
    }
}
