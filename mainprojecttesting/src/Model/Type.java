package Model;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class Type {

    private int id;
    private int noOfBeds;
    private double pricePerDay;

    public Type(int id,
                int no_of_beds,
                double price_per_day){

        this.id = id;
        this.noOfBeds = no_of_beds;
        this.pricePerDay = price_per_day;

    }

    public int getNoOfBeds() {
        return noOfBeds;
    }

    public void setNoOfBeds(int noOfBeds) {
        this.noOfBeds = noOfBeds;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
