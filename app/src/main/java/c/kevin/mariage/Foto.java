package c.kevin.mariage;

public class Foto {

    public String idF;
    public String nameF;
    public String phoneF;
    public String adressF;
    public String emailF;
    public String noteF;
    public String priceF;

    public Foto(){

    }


    public Foto(String idF, String nameF, String phoneF, String adressF, String emailF, String noteF ,String priceF) {
        this.idF = idF;
        this.nameF = nameF;
        this.phoneF = phoneF;
        this.adressF = adressF;
        this.emailF = emailF;
        this.noteF = noteF;
        this.priceF= priceF;
    }

    public String getIdF() {
        return idF;
    }
    public String getNameF() {
        return nameF;
    }
    public String getPhoneF() {
        return phoneF;
    }
    public String getAdressF() {
        return adressF;
    }
    public String getEmailF() {
        return emailF;
    }
    public String getNoteF() {
        return noteF;
    }
    public String getPriceF() {
        return priceF;
    }

    public void setIdF(String idF) {
        this.idF = idF;
    }
    public void setNameF(String nameF) {
        this.nameF = nameF;
    }
    public void setPhoneF(String phoneF) {
        this.phoneF = phoneF;
    }
    public void setAdressF(String adressF) {
        this.adressF = adressF;
    }
    public void setEmailF(String emailF) {
        this.emailF = emailF;
    }
    public void setNoteF(String noteF) {
        this.noteF = noteF;
    }
    public void setPriceF(String priceF) {
        this.priceF = priceF;
    }

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
