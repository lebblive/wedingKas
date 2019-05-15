package c.kevin.mariage;

import androidx.annotation.NonNull;

public class User {
    String name;
    String email;
    String uid;


    //ctor
    User(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    // geter
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getUid() {
        return uid;
    }

    //seter
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    //toString
    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
