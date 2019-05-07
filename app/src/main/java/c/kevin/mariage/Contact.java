package c.kevin.mariage;

public class Contact {

    public String idC;
    public String firstName;
    public String familyName;
    public String PhoneNumberContact;
    public String sex;
    public String age;
    public String NoteContact;

    public Contact(){

    }

    public Contact(String idC, String firstName, String familyName, String phoneNumberContact, String sex, String age, String NoteContact) {
        this.idC = idC;
        this.firstName = firstName;
        this.familyName = familyName;
        PhoneNumberContact = phoneNumberContact;
        this.sex = sex;
        this.age = age;
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

    public void setIdC(String idC) {
        this.idC = idC;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setPhoneNumberContact(String phoneNumberContact) {
        PhoneNumberContact = phoneNumberContact;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setNoteContact(String noteContact) {
        NoteContact = noteContact;
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
