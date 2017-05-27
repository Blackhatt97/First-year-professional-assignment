package Model;

/**
 * Created by blackhatt on 14/05/2017.
 */
public class Extras {

    private int id;
    private String type;
    private String name;
    private int price;

    public Extras(int id,
                  String name,
                  String type,
                  int price){
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return id + ": " + name;
    }
}

