package com.example.a48900.ledsistem.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class User {
    public String username;
   // public String password;
    public Date date;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String username, Date date) {
        this.username = username;
        this.date = date;
       // this.password = password;
    }
}
