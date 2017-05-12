package DBW;

/**
 * Created by Jakub Petrík on 12/05/2017.
 */
public class DBSingleton {

    private static DBConn DBConnection;

    public static DBConn getDBConnection() {

        if (DBConnection == null) {

            DBConnection = new DBConn();
            System.out.println("New DB connection established.");

        }

        else {

            System.out.println("Connection already established, returning connection");

        }

        return DBConnection;

    }
}
