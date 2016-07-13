package com.avinsharma.notify;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Avin on 22-05-2016.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public User() {

        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}