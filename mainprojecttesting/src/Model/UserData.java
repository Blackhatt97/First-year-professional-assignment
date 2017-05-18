package Model;

import Model.DBWrapper.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserData {

    private ObservableList<User> userList = FXCollections.observableArrayList();

    public ObservableList<User> getUserList() {
        return userList;
    }

    public void loadList() {
        DBConn dbConn = new DBConn();
        userList = dbConn.getAllUsers();
        dbConn = null;
    }

}