package Model;

import java.sql.Date;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class Reservation {

    private int id;
    private int custId;
    private Date currentDate;
    private Date startDate;
    private Date endDate;
    private double pickup;
    private double dropoff;
    private int type;


    public Reservation(int id,
                  int cust_id,
                  Date currentDate,
                  Date start_date,
                  Date end_date,
                  double pickup,
                  double dropoff,
                  int type){

        this.id = id;
        this.custId = cust_id;
        this.currentDate = currentDate;
        this.startDate = start_date;
        this.endDate = end_date;
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

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
