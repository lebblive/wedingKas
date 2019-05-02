package c.kevin.mariage;

public class Other {
    public String idO;
    public String nameO;
    public String phoneO;
    public String adressO;
    public String emailO;
    public String noteO;
    public String priceO;

    public Other(){

    }

    public Other(String idO, String nameO, String phoneO, String adressO, String emailO, String noteO, String priceO) {
        this.idO = idO;
        this.nameO = nameO;
        this.phoneO = phoneO;
        this.adressO = adressO;
        this.emailO = emailO;
        this.noteO = noteO;
        this.priceO = priceO;
    }

    public String getIdO() {
        return idO;
    }
    public String getNameO() {
        return nameO;
    }
    public String getPhoneO() {
        return phoneO;
    }
    public String getAdressO() {
        return adressO;
    }
    public String getEmailO() {
        return emailO;
    }
    public String getNoteO() {
        return noteO;
    }
    public String getPriceO() {
        return priceO;
    }

    public void setIdO(String idO) {
        this.idO = idO;
    }
    public void setNameO(String nameO) {
        this.nameO = nameO;
    }
    public void setPhoneO(String phoneO) {
        this.phoneO = phoneO;
    }
    public void setAdressO(String adressO) {
        this.adressO = adressO;
    }
    public void setEmailO(String emailO) {
        this.emailO = emailO;
    }
    public void setNoteO(String noteO) {
        this.noteO = noteO;
    }
    public void setPriceO(String priceO) {
        this.priceO = priceO;
    }

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
