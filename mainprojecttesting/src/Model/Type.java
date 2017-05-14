package Model;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class Type {

    private int id;
    private int no_of_beds;
    private double price_per_day;

    public Type(int id,
                int no_of_beds,
                double price_per_day){

        this.id = id;
        this.no_of_beds = no_of_beds;
        this.price_per_day = price_per_day;

    }

    public int getNo_of_beds() {
        return no_of_beds;
    }

    public void setNo_of_beds(int no_of_beds) {
        this.no_of_beds = no_of_beds;
    }

    public double getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(double price_per_day) {
        this.price_per_day = price_per_day;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
