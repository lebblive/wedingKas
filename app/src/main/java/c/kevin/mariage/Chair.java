package c.kevin.mariage;

import androidx.annotation.NonNull;

public class Chair {
    private String idChair;
    private String firstNameChair;
    private String familyNameChair;
    private String tableName;

    public Chair() {
    }

    Chair(String idChair, String firstNameChair, String familyNameChair, String tableName) {
        this.idChair = idChair;
        this.firstNameChair = firstNameChair;
        this.familyNameChair = familyNameChair;
        this.tableName = tableName;
    }

    String getIdChair() {
        return idChair;
    }

    String getFirstNameChair() {
        return firstNameChair;
    }

    String getFamilyNameChair() {
        return familyNameChair;
    }

    String getTableName() {
        return tableName;
    }

    @NonNull

    @Override
    public String toString() {
        return "Chair{" +
                "idChair='" + idChair + '\'' +
                ", firstNameChair='" + firstNameChair + '\'' +
                ", familyNameChair='" + familyNameChair + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
