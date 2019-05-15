package c.kevin.mariage;

public class Foto {

    private String idF;
    private String nameF;
    private String phoneF;
    private String adressF;
    private String emailF;
    private String noteF;
    private String priceF;

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
