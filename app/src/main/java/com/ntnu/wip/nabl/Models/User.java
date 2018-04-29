package com.ntnu.wip.nabl.Models;

public class User {
    private String name;
    private ContactInformation contactInformation;


    public User(String name, ContactInformation contactInformation) {
        this.name = name;
        this.contactInformation = contactInformation;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public String getName() {
        return name;
    }
}
