package Model;

import java.sql.Date;
import java.util.ArrayList;

public class Rental {

    private int id;
    private int custId;
    private Date currentDate;
    private Date startDate;
    private Date endDate;
    private int pickup;
    private int dropoff;
    private ArrayList<Extras> extra;
    private int motorhomeId;
    private String season;

    public Rental(int id,
                  int custId,
                  Date currentDate,
                  Date startDate,
                  Date end_date,
                  int pickup,
                  int dropoff,
                  int motorhomeId,
                  String season) {

        this.id = id;
        this.custId = custId;
        this.currentDate = currentDate;
        this.startDate = startDate;
        this.endDate = end_date;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.extra = null;
        this.motorhomeId = motorhomeId;
        this.season = season;

    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getMotorhomeId() {
        return motorhomeId;
    }

    public void setMotorhomeId(int motorhomeId) {
        this.motorhomeId = motorhomeId;
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

    public int getPickup() {
        return pickup;
    }

    public void setPickup(int pickup) {
        this.pickup = pickup;
    }

    public int getDropoff() {
        return dropoff;
    }

    public void setDropoff(int dropoff) {
        this.dropoff = dropoff;
    }

    public ArrayList<Extras> getExtra() {
        return extra;
    }

    public void setExtra(ArrayList<Extras> extra) {
        this.extra = extra;
    }
}
