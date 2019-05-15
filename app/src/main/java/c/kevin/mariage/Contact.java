package c.kevin.mariage;

public class Contact {

    private String idC;
    private String firstName;
    private String familyName;
    private String PhoneNumberContact;
    private String sex;
    private String age;
    private String NoteContact;

    public Contact(){}

    public Contact(String idC, String firstName, String familyName, String phoneNumberContact, String sex, String age, String NoteContact) {
        this.idC = idC;
        this.firstName = firstName;
        this.familyName = familyName;
        this.PhoneNumberContact = phoneNumberContact;
        this.sex = sex;
        this.age = age;
        this.NoteContact=NoteContact;
    }

    public String getIdC() {
        return idC;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getPhoneNumberContact() {
        return PhoneNumberContact;
    }

    public String getSex() {
        return sex;
    }

    public String getAge() {
        return age;
    }

    public String getNoteContact() {
        return NoteContact;
    }



    @Override
    public String toString() {
        return "Contact{" +
                "idC='" + idC + '\'' +
                ", firstName='" + firstName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", PhoneNumberContact='" + PhoneNumberContact + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", NoteContact='" + NoteContact + '\'' +
                '}';
    }
}
