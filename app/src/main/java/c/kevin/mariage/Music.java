package c.kevin.mariage;

public class Music {

    public String idM;
    public String nameM;
    public String phoneM;
    public String adressM;
    public String emailM;
    public String noteM;
    public String priceM;

    public Music(){

    }

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

    public void setIdM(String idM) {
        this.idM = idM;
    }
    public void setNameM(String nameM) {
        this.nameM = nameM;
    }
    public void setPhoneM(String phoneM) {
        this.phoneM = phoneM;
    }
    public void setAdressM(String adressM) {
        this.adressM = adressM;
    }
    public void setEmailM(String emailM) {
        this.emailM = emailM;
    }
    public void setNoteM(String noteM) {
        this.noteM = noteM;
    }
    public void setPriceM(String priceM) {
        this.priceM = priceM;
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
