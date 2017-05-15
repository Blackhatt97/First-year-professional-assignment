package Model;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class Motorhome {
    private int id;
    private String brand;
    private int fab_year;
    private String reg_plate;
    private int mileage;
    private String status;

    public Motorhome(int id,
                     String brand,
                     int fab_year,
                     String reg_plate,
                     int mileage,
                     String status){

        this.id = id;
        this.brand = brand;
        this.fab_year = fab_year;
        this.reg_plate = reg_plate;
        this.mileage = mileage;
        this.status = status;
    }



    public String getReg_plate() {
        return reg_plate;
    }

    public void setReg_plate(String reg_plate) {
        this.reg_plate = reg_plate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getFab_year() {
        return fab_year;
    }

    public void setFab_year(int fab_year) {
        this.fab_year = fab_year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
