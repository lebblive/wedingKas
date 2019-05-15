package c.kevin.mariage;

import androidx.annotation.NonNull;

public class Music {

    private String idM;
    private String nameM;
    private String phoneM;
    private String adressM;
    private String emailM;
    private String noteM;
    private String priceM;

    Music(String idM, String nameM, String phoneM, String adressM, String emailM, String noteM, String priceM) {
        this.idM = idM;
        this.nameM = nameM;
        this.phoneM = phoneM;
        this.adressM = adressM;
        this.emailM = emailM;
        this.noteM = noteM;
        this.priceM = priceM;
    }

    String getNameM() {
        return nameM;
    }
    String getPhoneM() {
        return phoneM;
    }
    String getAdressM() {
        return adressM;
    }
    String getEmailM() {
        return emailM;
    }
    String getNoteM() {
        return noteM;
    }
    String getPriceM() {
        return priceM;
    }



    @NonNull
    @Override
    public String toString() {
        return "Music{" +
                "idM='" + idM + '\'' +
                ", nameM='" + nameM + '\'' +
                ", phoneM='" + phoneM + '\'' +
                ", adressM='" + adressM + '\'' +
                ", emailM='" + emailM + '\'' +
                ", noteM='" + noteM + '\'' +
                ", priceM='" + priceM + '\'' +
                '}';
    }

}
