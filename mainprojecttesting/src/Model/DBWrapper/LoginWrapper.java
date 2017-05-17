package Model.DBWrapper;

import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static Model.ErrorHandler.popUp;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class LoginWrapper {

    public User loginAuthentication(String userName, String password){
        DBConn dbConn = new DBConn();
        Connection conn = null;
        try {
            conn = dbConn.getConn();
            String query = "SELECT * FROM `users` " +
                    "WHERE `email` = ? AND `pass` = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, userName);
            ps.setString(2, password);
            ps.execute();

            ResultSet rs = ps.getResultSet();
            if (rs.first()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getDate(4));

            } else
                popUp("Failed", "Invalid username and/or password", "Close");

        } catch (Exception ex) {
            ex.printStackTrace(); // to do : popup error could not login coz of fxml not found
        }

        return null;
    }


}
