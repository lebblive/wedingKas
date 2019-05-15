package c.kevin.mariage;

import androidx.annotation.NonNull;

public class Place {
    private String idP;
    private String nameP;
    private String phoneP;
    private String adressP;
    private String emailP;
    private String noteP;
    private String priceP;

    public Place(){}

    Place(String idP, String nameP, String phoneP, String adressP, String emailP, String noteP, String priceP) {
        this.idP = idP;
        this.nameP = nameP;
        this.phoneP = phoneP;
        this.adressP = adressP;
        this.emailP = emailP;
        this.noteP = noteP;
        this.priceP = priceP;
    }

    public String getIdP() {
        return idP;
    }
    String getNameP() {
        return nameP;
    }
    String getPhoneP() {
        return phoneP;
    }
    String getAdressP() {
        return adressP;
    }
    String getEmailP() {
        return emailP;
    }
    String getNoteP() {
        return noteP;
    }
    String getPriceP() {
        return priceP;
    }



    @NonNull
    @Override
    public String toString() {
        return "Place{" +
                "idP='" + idP + '\'' +
                ", nameP='" + nameP + '\'' +
                ", phoneP='" + phoneP + '\'' +
                ", adressP='" + adressP + '\'' +
                ", emailP='" + emailP + '\'' +
                ", noteP='" + noteP + '\'' +
                ", priceP='" + priceP + '\'' +
                '}';
    }
}
