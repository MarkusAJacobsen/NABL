package com.ntnu.wip.nabl.Models;

import android.support.v4.content.ContextCompat;

/**
 * Model for a contact information
 * Created by markusja on 4/11/18.
 */
public class ContactInformation {
    private String email;
    private int phoneNumber;

    /**
     * Empty constructor
     */
    public ContactInformation() {}

    /**
     * Full constructor
     * @param email String
     * @param phoneNumber int
     */
    public ContactInformation(String email, int phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter for email
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for phone number
     * @return int
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter for phone number
     * @param phoneNumber int
     */
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
