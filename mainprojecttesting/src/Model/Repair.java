package Model;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class Repair {

    private int id;
    private String type;
    private int price;
    private String description;
    private int mhIdFk;

    public Repair(int id,
                  String type,
                  int price,
                  String description,
                  int mhIdFk){

        this.id = id;
        this.type = type;
        this.description = description;
        this.price = price;
        this.mhIdFk = mhIdFk;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMhIdFk() {
        return mhIdFk;
    }

    public void setMhIdFk(int mhIdFk) {
        this.mhIdFk = mhIdFk;
    }
}

