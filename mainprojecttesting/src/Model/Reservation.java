package Model;

import java.sql.Date;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class Reservation {

    private int id;
    private int custId;
    private Date reservationDate;
    private Date startDate;
    private Date endDate;
    private double pickup;
    private double dropoff;
    private int motorhomeId;
    private String season;


    public Reservation(int id,
                  int custId,
                  Date reservationDate,
                  Date startDate,
                  Date endDate,
                  double pickup,
                  double dropoff,
                  int motorhomeId,
                  String season){

        this.id = id;
        this.custId = custId;
        this.reservationDate = reservationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.motorhomeId = motorhomeId;
        this.season = season;

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

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
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

    public int getMotorhomeId() {
        return motorhomeId;
    }

    public void setMotorhomeId(int motorhomeId) {
        this.motorhomeId = motorhomeId;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
