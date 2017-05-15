package Model.DBWrapper;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {

    private static final String URL = "jdbc:mysql://fypanm.c8w1xrl7tum6.eu-central-1.rds.amazonaws.com:3306/";
    private static final String DB_NAME = "motorhome_system";
    private static final String USER = "mhadmin";
    private static final String PASS = "firstyearkea2k17";


    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    DBConn.URL + DBConn.DB_NAME,
                    DBConn.USER,
                    DBConn.PASS);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getDbName() {
        return DB_NAME;
    }

    public static void deleteAction(){}
}

