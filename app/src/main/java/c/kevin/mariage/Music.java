package c.kevin.mariage;

public class Music {

    private String idM;
    private String nameM;
    private String phoneM;
    private String adressM;
    private String emailM;
    private String noteM;
    private String priceM;

    public Music(){}

    public Music(String idM, String nameM, String phoneM, String adressM, String emailM, String noteM, String priceM) {
        this.idM = idM;
        this.nameM = nameM;
        this.phoneM = phoneM;
        this.adressM = adressM;
        this.emailM = emailM;
        this.noteM = noteM;
        this.priceM = priceM;
    }

    public String getIdM() {
        return idM;
    }
    public String getNameM() {
        return nameM;
    }
    public String getPhoneM() {
        return phoneM;
    }
    public String getAdressM() {
        return adressM;
    }
    public String getEmailM() {
        return emailM;
    }
    public String getNoteM() {
        return noteM;
    }
    public String getPriceM() {
        return priceM;
    }



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
