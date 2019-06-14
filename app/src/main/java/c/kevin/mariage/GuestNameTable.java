package c.kevin.mariage;

import androidx.annotation.NonNull;

public class GuestNameTable {
    private String idGuest;
    private String firstNameGuest;
    private String nameGuest;

    GuestNameTable(String idGuest, String firstNameGuest, String nameGuest) {
        this.idGuest = idGuest;
        this.firstNameGuest = firstNameGuest;
        this.nameGuest = nameGuest;
    }

    String getIdGuest() {
        return idGuest;
    }
    String getFirstNameGuest() {
        return firstNameGuest;
    }
    String getNameGuest() {
        return nameGuest;
    }

    @NonNull
    @Override
    public String toString() {
        return "GuestNameTable{" +
                "idGuest='" + idGuest + '\'' +
                ", firstNameGuest='" + firstNameGuest + '\'' +
                ", nameGuest='" + nameGuest + '\'' +
                '}';
    }
}
