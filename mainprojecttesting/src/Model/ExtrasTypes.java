package Model;

/**
 * Created by CIA on 18/05/2017.
 */
public enum ExtrasTypes {

    Table("Table"),
    Grill("Grill"),
    Chair("Chair"),
    Bike("Bike"),
    Tent("Tent"),
    Bikerack("Bikerack"),
    Boat("Boat"),
    Matress("Mattress");

    private String value;

    ExtrasTypes(String value) {

        this.value = value;

    }

    @Override
    public String toString() {

        return value;

    }

}
