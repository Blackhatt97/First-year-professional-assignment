package Model;

/**
 * Created by blackhatt on 17/05/2017.
 */
public class TypeName {
    private int id;
    private String text;

    public TypeName(int id, String text){

        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
