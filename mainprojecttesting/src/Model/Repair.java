package Model;

import java.sql.Date;

public class Repair {

    private int id;
    private int type;
    private double price;
    private String description;
    private int mhIdFk;
    private Date repDate;
    private String plate;


    public Repair(int id,
                  int mhIdFk,
                  int type,
                  String plate,
                  double price,
                  String description,
                  Date repDate) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.price = price;
        this.mhIdFk = mhIdFk;
        this.repDate = repDate;
        this.plate = plate;
    }

    public String getPlate() {return plate;}

    public void setPlate(String plate) {this.plate = plate;}

    public Date getRepDate() {
        return repDate;
    }

    public void setRepDate(Date repDate) {
        this.repDate = repDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMhId() {
        return mhIdFk;
    }

    public void setMhId(int mhIdFk) {
        this.mhIdFk = mhIdFk;
    }
}

