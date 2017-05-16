package Model.DBWrapper;

import Model.Motorhome;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBConn {

    private final String URL = "jdbc:mysql://fypanm.c8w1xrl7tum6.eu-central-1.rds.amazonaws.com:3306/";
    private final String DB_NAME = "motorhome_system";
    private final String USER = "mhadmin";
    private final String PASS = "firstyearkea2k17";


    public Connection getConn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    this.URL + this.DB_NAME,
                    this.USER,
                    this.PASS);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String getDbName() {
        return DB_NAME;
    }

    public void deleteFromDB(int id, String tableName){

        Connection con = getConn();

        String deleteQuery = "DELETE FROM `"+ tableName + "` WHERE `id` = ? ;";
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(deleteQuery);
            ps.setInt(1,id);
            ps.execute();
            con.close();
            
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }


    }

    public void addMotorHomeToDB(String brand, int fab_year, String reg_plate, int mileage, int status) {

        Connection connection = getConn();
        String sql = "INSERT INTO `motorhomes` (`brand`, `fab_year`, `mileage`, `mh_status`, `plate`) " +
                "VALUES ( ?,  ?,  ?, ?, ?);";
        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, brand);
            ps.setInt(2, fab_year);
            ps.setInt(3, mileage);
            ps.setInt(4, status);
            ps.setString(5, reg_plate);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<Motorhome> getAllMotorHomes(){

        ObservableList<Motorhome> motorhomes = FXCollections.observableArrayList();
        String sql = "SELECT * FROM motorhomes";
        ResultSet resultSet = sqlQueryWithReturn(sql);

        try {
            while (resultSet.next()){
                Motorhome motorhome = new Motorhome(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(6),
                        resultSet.getInt(4),
                        resultSet.getInt(5));
                motorhomes.add(motorhome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < motorhomes.size() ; i++) {
            System.out.println(motorhomes.get(i).getReg_plate());
        }
        return motorhomes;
    }

    public ResultSet sqlQueryWithReturn(String sqlQuery) {

        ResultSet resultSet = null;

        try {

            Connection connection = getConn();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();
            //connection.close();
            return resultSet;

        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        }

    }

}

