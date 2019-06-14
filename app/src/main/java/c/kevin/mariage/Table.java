package c.kevin.mariage;

import androidx.annotation.NonNull;

public class Table {
    private String idT;
    private String nameT;
    private String placeT;
    public Table(){}

    Table(String idT, String nameT, String placeT) {
        this.idT = idT;
        this.nameT = nameT;
        this.placeT = placeT;
    }

    String getIdT() {
        return idT;
    }
    String getNameT() {
        return nameT;
    }
    String getPlaceT() {
        return placeT;
    }

    @NonNull

    @Override
    public String toString() {
        return "Table{" +
                "idT='" + idT + '\'' +
                ", nameT='" + nameT + '\'' +
                ", placeT='" + placeT + '\'' +
                '}';
    }
}
