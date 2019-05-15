package c.kevin.mariage;

import androidx.annotation.NonNull;

public class Other {
    private String idO;
    private String nameO;
    private String phoneO;
    private String adressO;
    private String emailO;
    private String noteO;
    private String priceO;

    Other(String idO, String nameO, String phoneO, String adressO, String emailO, String noteO, String priceO) {
        this.idO = idO;
        this.nameO = nameO;
        this.phoneO = phoneO;
        this.adressO = adressO;
        this.emailO = emailO;
        this.noteO = noteO;
        this.priceO = priceO;
    }

    String getNameO() {
        return nameO;
    }
    String getPhoneO() {
        return phoneO;
    }
    String getAdressO() {
        return adressO;
    }
    String getEmailO() {
        return emailO;
    }
    String getNoteO() {
        return noteO;
    }
    String getPriceO() {
        return priceO;
    }



    @NonNull
    @Override
    public String toString() {
        return "Other{" +
                "idO='" + idO + '\'' +
                ", nameO='" + nameO + '\'' +
                ", phoneO='" + phoneO + '\'' +
                ", adressO='" + adressO + '\'' +
                ", emailO='" + emailO + '\'' +
                ", noteO='" + noteO + '\'' +
                ", priceO='" + priceO + '\'' +
                '}';
    }
}
