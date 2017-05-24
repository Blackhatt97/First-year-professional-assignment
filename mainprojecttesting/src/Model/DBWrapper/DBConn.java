package Model.DBWrapper;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.sql.*;
import java.sql.Date;
import java.util.*;

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

    public void addReservationToDB(int customerID,
                                   java.sql.Date reservationDate,
                                   java.sql.Date startDate,
                                   java.sql.Date endDate,
                                   int pickup,
                                   int dropoff,
                                   int motorhomeID){

        Connection connection = getConn();
        String sql = "INSERT INTO `reservations` (`cust_id`, `date_res`, `st_date`, `end_date`, `pickup`, `dropoff`, `motorhome_id`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerID);
            preparedStatement.setDate(2, reservationDate);
            preparedStatement.setDate(3, startDate );
            preparedStatement.setDate(4, endDate);
            preparedStatement.setInt(5, pickup);
            preparedStatement.setInt(6, dropoff);
            preparedStatement.setInt(7, motorhomeID);
            preparedStatement.execute();
            connection.close();

        } catch (java.sql.SQLException e){
            e.printStackTrace();
        }




    }

    public String getDbName() {
        return DB_NAME;
    }

    public void addExtrasToDB(String type, String name, double price, int isRented){

        Connection connection = getConn();
        String sql = "INSERT INTO `extras` (`type`, `name`, `price`, `isRented`) " +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, isRented);
            preparedStatement.execute();
            connection.close();

        } catch (java.sql.SQLException e){
            e.printStackTrace();
        }

    }

    public void deleteFromDB(int id, String tableName) {

        Connection con = getConn();

        String deleteQuery = "DELETE FROM `" + tableName + "` WHERE `id` = ?";
        PreparedStatement ps = null;
        try {

            ps = con.prepareStatement(deleteQuery);
            ps.setInt(1, id);
            ps.execute();
            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void addMotorHomeToDB(String brand, int fabYear, String regPlate, int mileage, int status, int type) {

        Connection connection = getConn();
        String sql = "INSERT INTO `motorhomes` (`brand`, `fab_year`, `mileage`, `mh_status`, `plate`) " +
                "VALUES (?, ?, ?, ?, ?)";
        String typeSql = "INSERT INTO `motorhometype` (`motorhome_id`, `type_id`)" +
                "VALUES (?, ?)";

        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, brand);
            ps.setInt(2, fabYear);
            ps.setInt(3, mileage);
            ps.setInt(4, status);
            ps.setString(5, regPlate);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            int id = 0;
            if (rs != null && rs.next()) {
                id = rs.getInt(1);
            }
            if (id > 0) {
                ps = connection.prepareStatement(typeSql);
                ps.setInt(1, id);
                ps.setInt(2, type);
                ps.execute();
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addCustomerToDB(String fname, String lname, java.sql.Date dateBirth, String email, String address) {

        Connection connection = getConn();
        String sql = "INSERT INTO `customers` (`f_name`, `l_name`, `date_birth`, `email`, `address`) " +
                "VALUES ( ?,  ?,  ?, ?, ?);";

        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setDate(3, dateBirth);
            ps.setString(4, email);
            ps.setString(5, address);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addRepairToDB(int mhIdFk, int type, double price, String descr, java.sql.Date repDate){

        Connection con = getConn();
        String sql = "INSERT INTO `repairs` (`id`, `mh_id`, `type`, `plate`, `price`, `descr`,`date`)" +
                " VALUES (null, ?, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, mhIdFk);
            ps.setInt(2, type);
            ps.setString(3, getPlate(mhIdFk));
            ps.setDouble(4, price);
            ps.setString(5,descr);
            ps.setDate(6,repDate);
            ps.execute();
            con.close();

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public ArrayList<Pair<Integer, String>> getMotorhomeTypes() {

        ArrayList<Pair<Integer, String>> types = new ArrayList<>();
        String sql = "SELECT * FROM types";

        try {
            Connection connection = getConn();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pair<Integer, String> pair = new Pair<Integer, String>(
                        resultSet.getInt("id"),
                        resultSet.getString("name") + " - " +
                                resultSet.getInt("no_beds") + " beds - Price: " +
                                resultSet.getFloat("price_day")
                ) {
                    @Override
                    public String toString() {
                        return this.getKey() + " " + this.getValue();
                    }
                };
                types.add(pair);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;

    }

    public ArrayList<Pair<Integer, String>> getRepairTypes() {

        ArrayList<Pair<Integer, String>> repairtype = new ArrayList<>();
        String sql = "SELECT * FROM repairtype";

        try {
            Connection connection = getConn();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pair<Integer, String> pair = new Pair<Integer, String>(
                        resultSet.getInt("id"),
                        resultSet.getString("type")
                ) {
                    @Override
                    public String toString() {
                        return this.getKey() + " " + this.getValue();
                    }
                };
                repairtype.add(pair);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return repairtype;

    }

    public ArrayList<Integer> getMotorhomeId(){

        ArrayList<Integer> ids = new ArrayList<>();
        String sql = "SELECT * FROM `motorhomes` ";
        try{
            Connection con = getConn();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                ids.add(rs.getInt(1));
            }
            con.close();

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return ids;
    }


    public ArrayList<Pair<Integer, String>> getMotorhomeStatuses() {

        ArrayList<Pair<Integer, String>> statuses = new ArrayList<>();
        String sql = "SELECT * FROM status ORDER BY id";

        try {
            Connection connection = getConn();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pair<Integer, String> pair = new Pair<Integer, String>(
                        resultSet.getInt("id"),
                        resultSet.getString("description")
                ) {
                    @Override
                    public String toString() {
                        return this.getKey() + " " + this.getValue();
                    }
                };
                statuses.add(pair);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statuses;

    }

    public ObservableList<Motorhome> getAllMotorHomes(int typeId) {

        ObservableList<Motorhome> motorhomes = FXCollections.observableArrayList();

        String sql = "SELECT * FROM motorhomes JOIN motorhometype ON motorhomes.id = motorhometype.motorhome_id";
        if (typeId > -1) {
            sql += " WHERE type_id = ?";
        }
            sql += " ORDER BY id DESC";

        try {
            Connection connection = getConn();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (typeId > -1) {
                preparedStatement.setInt(1, typeId);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Motorhome motorhome = new Motorhome(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getInt(8)
                );
                motorhomes.add(motorhome);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return motorhomes;
    }

    public int getStatus(int mhIdFk){

        String sql = "SELECT * FROM `motorhomes` WHERE `id` = ? ;";
        try{
            int status = 0;
            Connection con = getConn();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,mhIdFk);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                status = rs.getInt(5);
            }
            con.close();
            return status;

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return -1;
    }


    public String getPlate(int mhId){


        String sql = "SELECT * FROM `motorhomes` WHERE `id` = ?";

        try{
            String plate = null;
            Connection con = getConn();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,mhId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                plate = rs.getString(6);
            }
            con.close();
            return plate;

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
         return null;
    }
    public ObservableList<Repair> getAllRepairs(int mhId) {

        ObservableList<Repair> repairs = FXCollections.observableArrayList();
        String sql = "SELECT * FROM repairs WHERE mh_id = ? ORDER BY id DESC ";

        try {
            Connection connection = getConn();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,mhId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Repair repair = new Repair(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getDate(7));
                repairs.add(repair);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return repairs;
    }


    public ObservableList<Repair> getAllRepairs() {

        ObservableList<Repair> repairs = FXCollections.observableArrayList();
        String sql = "SELECT * FROM repairs ORDER BY id DESC ";

        try {
            Connection connection = getConn();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Repair repair = new Repair(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getDate(7));
                repairs.add(repair);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return repairs;
    }

    public ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers ORDER BY id DESC";

        try {
            Connection connection = getConn();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
                Customer customer = new Customer(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6));
                customers.add(customer);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void updateMotorHome(int id, int status, String plate, int type,
                                String brand, int fabYear, int kilometrage) {

        Connection connection = getConn();
        String sql = "UPDATE motorhomes SET mh_status = ?, plate = ?, brand = ?," +
                "fab_year= ?, mileage = ? WHERE id = ?";
        String typeSql = "UPDATE motorhometype SET type_id = ? WHERE motorhome_id = ?";
        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setString(2, plate);
            ps.setString(3, brand);
            ps.setInt(4, fabYear);
            ps.setInt(5, kilometrage);
            ps.setInt(6, id);
            ps.execute();

            ps = connection.prepareStatement(typeSql);
            ps.setInt(1, type);
            ps.setInt(2, id);
            ps.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateCustomer(int id,
                               String fname,
                               String lname,
                               java.sql.Date dateBirth,
                               String address,
                               String email) {

        Connection connection = getConn();
        String sql = "UPDATE customers SET f_name = ?, l_name = ?, date_birth = ?," +
                "email = ?, address = ? WHERE id = ?";
        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setDate(3, dateBirth);
            ps.setString(4, email);
            ps.setString(5, address);
            ps.setInt(6, id);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateRepair(int id,
                              int mhId,
                              int repairType,
                              double price,
                              String descr,
                              Date date,
                              int status){

        Connection connection = getConn();
        String sql = "UPDATE repairs SET mh_id = ?, type = ?, plate = ?," +
                "price = ?, descr = ?, date = ? WHERE id = ?";
        String sql2 = "UPDATE motorhomes SET mh_status = ?;";
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;

        try {

            ps2 = connection.prepareStatement(sql2);
            ps2.setInt(1,status);
            ps2.execute();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, mhId);
            ps.setInt(2, repairType);
            ps.setString(3, getPlate(mhId));
            ps.setDouble(4, price);
            ps.setString(5, descr);
            ps.setDate(6,date);
            ps.setInt(7, id);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public ObservableList<User> getAllUsers(){

        ObservableList<User> usersOL = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";

        try {
            Connection connection = getConn();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7));
                usersOL.add(user);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < usersOL.size() ; i++) {
            System.out.println(usersOL.get(i).getLname());
        }

        return usersOL;
    }


    public void updateUser(int id, String fname, String lname, String email,
                           String type, String address, java.sql.Date date_birth, String newPass) {

        Connection connection = getConn();
        String sql = "UPDATE `users` SET `f_name` = ?, `l_name` = ?, `email` = ?," +
                "`type_user`= ?, `address` = ?, `date_birth` = ?, `pass` = MD5(?)  WHERE `id` = ?";
        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1,fname );
            ps.setString(2, lname);
            ps.setString(3, email);
            ps.setString(4, type);
            ps.setString(5, address);
            ps.setDate(6, date_birth);
            ps.setString(7, newPass);
            ps.setInt(8, id);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addUserToDB(String fName, String lName, java.sql.Date dateBirth,
                            String email, String address, String typeUser, String pass) {

        Connection connection = getConn();
        String sql = "INSERT INTO `users` (`f_name`, `l_name`, `date_birth`, `email`, `address`, `type_user`, `pass`) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, MD5(?))";
        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, fName);
            ps.setString(2, lName);
            ps.setDate(3, dateBirth);
            ps.setString(4, email);
            ps.setString(5, address);
            ps.setString(6, typeUser);
            ps.setString(7, pass);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<Extras> getAllExtras() {

        ObservableList<Extras> extras = FXCollections.observableArrayList();
        String sql = "SELECT * FROM extras";

        try {
            Connection connection = getConn();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Extras extra = new Extras(
                        resultSet.getInt(1),
                        resultSet.getString(3),
                        resultSet.getString(2),
                        resultSet.getInt(4)
                );
                extras.add(extra);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return extras;

    }

    @SuppressWarnings("DUPLICATES")
    public void updateExtras(int id,
                             String name,
                             String type,
                             double price) {

        Connection connection = getConn();
        String sql = "UPDATE `extras` SET `name` = ?, `type` = ?, `price` = ? WHERE `id` = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, id);
            preparedStatement.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}