package c.kevin.mariage;

import androidx.annotation.NonNull;

public class Foto {

    private String idF;
    private String nameF;
    private String phoneF;
    private String adressF;
    private String emailF;
    private String noteF;
    private String priceF;

    Foto(String idF, String nameF, String phoneF, String adressF, String emailF, String noteF, String priceF) {
        this.idF = idF;
        this.nameF = nameF;
        this.phoneF = phoneF;
        this.adressF = adressF;
        this.emailF = emailF;
        this.noteF = noteF;
        this.priceF= priceF;
    }

    String getNameF() {
        return nameF;
    }
    String getPhoneF() {
        return phoneF;
    }
    String getAdressF() {
        return adressF;
    }
    String getEmailF() {
        return emailF;
    }
    String getNoteF() {
        return noteF;
    }
    String getPriceF() {
        return priceF;
    }


    @NonNull
    @Override
    public String toString() {
        return "Foto{" +
                "idF='" + idF + '\'' +
                ", nameF='" + nameF + '\'' +
                ", phoneF='" + phoneF + '\'' +
                ", adressF='" + adressF + '\'' +
                ", emailF='" + emailF + '\'' +
                ", noteF='" + noteF + '\'' +
                ", priceF='" + priceF + '\'' +
                '}';
    }
}
