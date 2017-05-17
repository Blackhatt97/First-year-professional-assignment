package Model;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class Motorhome {
    private int id;
    private String brand;
    private int fabYear;
    private String regPlate;
    private int mileage;
    private int status;
    private int type;

    public Motorhome(int id,
                     String brand,
                     int fabYear,
                     String regPlate,
                     int mileage,
                     int status,
                     int type){

        this.id = id;
        this.brand = brand;
        this.fabYear = fabYear;
        this.regPlate = regPlate;
        this.mileage = mileage;
        this.status = status;
        this.type = type;
    }



    public String getRegPlate() {
        return regPlate;
    }

    public void setRegPlate(String regPlate) {
        this.regPlate = regPlate;
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

    public int getFabYear() {
        return fabYear;
    }

    public void setFabYear(int fabYear) {
        this.fabYear = fabYear;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
