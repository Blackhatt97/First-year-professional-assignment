package Model;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class Repair {

    private int id;
    private String type;
    private int price;
    private String description;
    private int mh_id_fk;

    public Repair(int id,
                  String type,
                  int price,
                  String description,
                  int mh_id_fk){

        this.id = id;
        this.type = type;
        this.description = description;
        this.price = price;
        this.mh_id_fk = mh_id_fk;
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

    public int getMh_id_fk() {
        return mh_id_fk;
    }

    public void setMh_id_fk(int mh_id_fk) {
        this.mh_id_fk = mh_id_fk;
    }
}

