package Model;

import javafx.util.Pair;
import java.util.ArrayList;

public interface DataInterface {

    ArrayList<Pair<Integer, String>> loadData();

    ArrayList<Pair<Integer, String>> getData();

}
