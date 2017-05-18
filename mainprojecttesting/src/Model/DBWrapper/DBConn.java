package Model.DBWrapper;

import Model.Customer;
import Model.Extras;
import Model.Motorhome;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;
import java.util.Date;

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

    public void createExtras(){

        Connection connection = getConn();
        String sql = "";

    }

    public void deleteFromDB(int id, String tableName){

        Connection con = getConn();

        String deleteQuery = "DELETE FROM `"+ tableName + "` WHERE `id` = ?";
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

    public ObservableList<Motorhome> getAllMotorHomes(){

        ObservableList<Motorhome> motorhomes = FXCollections.observableArrayList();
        String sql = "SELECT * FROM motorhomes JOIN motorhometype ON motorhomes.id = motorhometype.motorhome_id";

        try {
            Connection connection = getConn();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
        for (int i = 0; i < motorhomes.size() ; i++) {
            System.out.println(motorhomes.get(i).getRegPlate());
        }
        return motorhomes;
    }

    public ObservableList<Customer> getAllCustomers(){

        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";

        try {
            Connection connection = getConn();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
                Customer customer = new Customer(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(5));
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
                           String type, String address, java.sql.Date date_birth) {

        Connection connection = getConn();
        String sql = "UPDATE `users` SET `f_name` = ?, `l_name` = ?, `email` = ?," +
                "`type_user`= ?, `address` = ?, `date_birth` = ?  WHERE `id` = ?";
        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1,fname );
            ps.setString(2, lname);
            ps.setString(3, email);
            ps.setString(4, type);
            ps.setString(5, address);
            ps.setDate(6, date_birth);
            ps.setInt(7, id);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addUserToDB(String f_name, String l_name, java.sql.Date date_birth, String email, String address, String type_user, String pass) {

        Connection connection = getConn();
        String sql = "INSERT INTO `users` (`f_name`, `l_name`, `date_birth`, `email`, `address`, `type_user`, `pass`) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(sql);
            ps.setString(1, f_name);
            ps.setString(2, l_name);
            ps.setDate(3, date_birth);
            ps.setString(4, email);
            ps.setString(5, address);
            ps.setString(6, type_user);
            ps.setString(7, pass);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}