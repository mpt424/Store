package ddgm.store.utils;

import android.graphics.Bitmap;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by mpt on 28/12/2017.
 */
@IgnoreExtraProperties
public class User {
    public String getUserName() {
        return userName;
    }

    public String getFirsrName() {
        return firsrName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getEmail() {
        return email;
    }

    public boolean isManager() {
        return isManager;
    }

    public User(boolean isManager, String userName, String firsrName, String lastName, String address, String city, String street, String email) {
        this.isManager = isManager;
        this.userName = userName;
        this.firsrName = firsrName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.street = street;
        this.email = email;
    }


    private boolean isManager;
    private String userName;
    private String firsrName;
    private String lastName;
    private String address;
    private String city;
    private String street;
    private String email;

    public User(){

    }
}
