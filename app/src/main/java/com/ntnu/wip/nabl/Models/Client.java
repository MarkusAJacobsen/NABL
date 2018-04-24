package com.ntnu.wip.nabl.Models;


import java.util.Locale;

/**
 * Created by markusja on 4/11/18.
 */

public class Client {
    private String id;
    private String name;
    private ContactInformation contactInformation;
    private Address address;

    public Client(){
        this.contactInformation = new ContactInformation();
        this.address = new Address();
    }

    public Client(String id, String name, ContactInformation contactInformation, Address address) {
        this.id = id;
        this.name = name;
        this.contactInformation = contactInformation;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return this.name;
    }
}
