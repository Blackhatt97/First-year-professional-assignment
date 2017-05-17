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
}