package DBW;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Jakub Petrík on 12/05/2017.
 */
public class DBOpps {


    DBSingleton SDB;
    Connection connection = null;

    public void sqlQuery(String sqlQuery) {

        DBConn DbConnection = SDB.getDBConnection();

        try {

            connection = DbConnection.getConn();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sqlQuery);
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public ResultSet sqlQueryWithReturn(String sqlQuery) {

        DBConn DbConnection = SDB.getDBConnection();
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getConn();
            Statement stmt = connection.createStatement();
            resultSet = stmt.executeQuery(sqlQuery);
            //connection.close();
            return resultSet;

        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        }

    }
}
