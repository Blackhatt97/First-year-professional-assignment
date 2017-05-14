package Model;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class Reservation {

    private int id;
    private int cust_id;
    private Date current_date;
    private Date start_date;
    private Date end_date;
    private double pickup;
    private double dropoff;
    private int type;


    public Reservation(int id,
                  int cust_id,
                  Date current_date,
                  Date start_date,
                  Date end_date,
                  double pickup,
                  double dropoff,
                  int type){

        this.id = id;
        this.cust_id = cust_id;
        this.current_date = current_date;
        this.start_date = start_date;
        this.end_date = end_date;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.type = type;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public Date getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(Date current_date) {
        this.current_date = current_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public double getPickup() {
        return pickup;
    }

    public void setPickup(double pickup) {
        this.pickup = pickup;
    }

    public double getDropoff() {
        return dropoff;
    }

    public void setDropoff(double dropoff) {
        this.dropoff = dropoff;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
