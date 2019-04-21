package c.kevin.mariage;

public class Place {
    public String idP;
    public String nameP;
    public String phoneP;
    public String adressP;
    public String emailP;
    public String noteP;
    public String priceP;

    public Place(){

    }

    public Place(String idP, String nameP, String phoneP, String adressP, String emailP, String noteP, String priceP) {
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
    public String getNameP() {
        return nameP;
    }
    public String getPhoneP() {
        return phoneP;
    }
    public String getAdressP() {
        return adressP;
    }
    public String getEmailP() {
        return emailP;
    }
    public String getNoteP() {
        return noteP;
    }
    public String getPriceP() {
        return priceP;
    }

    public void setIdP(String idP) {
        this.idP = idP;
    }
    public void setNameP(String nameP) {
        this.nameP = nameP;
    }
    public void setPhoneP(String phoneP) {
        this.phoneP = phoneP;
    }
    public void setAdressP(String adressP) {
        this.adressP = adressP;
    }
    public void setEmailP(String emailP) {
        this.emailP = emailP;
    }
    public void setNoteP(String noteP) {
        this.noteP = noteP;
    }
    public void setPriceP(String priceP) {
        this.priceP = priceP;
    }

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
