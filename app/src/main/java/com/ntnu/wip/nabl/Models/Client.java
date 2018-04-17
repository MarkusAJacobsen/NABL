package com.ntnu.wip.nabl.Models;


import java.util.Locale;

/**
 * Created by markusja on 4/11/18.
 */

public class Client {
    private int id;
    private String givenName;
    private String surname;
    private ContactInformation contactInformation;
    private Address address;

    public Client(int id, String givenName, String surname, ContactInformation contactInformation, Address address) {
        this.id = id;
        this.givenName = givenName;
        this.surname = surname;
        this.contactInformation = contactInformation;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    @Override
    public String toString(){
        return String.format(Locale.getDefault(), "%s %s", this.givenName, this.surname);
    }
}
