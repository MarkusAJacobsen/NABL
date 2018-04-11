package com.ntnu.wip.nabl.DataModels;

import android.support.v4.content.ContextCompat;

/**
 * Created by markusja on 4/11/18.
 */

public class ContactInformation {
    private String email;
    private int phoneNumber;

    public ContactInformation(String email, int phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
