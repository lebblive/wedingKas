package c.kevin.mariage;

import androidx.annotation.NonNull;

public class Contact {

    private String idC;
    private String firstName;
    private String familyName;
    private String PhoneNumberContact;
    private String sex;
    private String age;
    private String NoteContact;


    Contact(String idC, String firstName, String familyName, String phoneNumberContact, String sex, String age, String NoteContact) {
        this.idC = idC;
        this.firstName = firstName;
        this.familyName = familyName;
        this.PhoneNumberContact = phoneNumberContact;
        this.sex = sex;
        this.age = age;
        this.NoteContact=NoteContact;
    }

    String getFirstName() {
        return firstName;
    }

    String getFamilyName() {
        return familyName;
    }

    String getPhoneNumberContact() {
        return PhoneNumberContact;
    }

    String getSex() {
        return sex;
    }

    String getAge() {
        return age;
    }

    String getNoteContact() {
        return NoteContact;
    }



    @NonNull
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
