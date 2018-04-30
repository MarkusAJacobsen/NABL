package com.ntnu.wip.nabl.Models;

public class User {
    private String id;
    private String name;
    private ContactInformation contactInformation;


    public User(String name, ContactInformation contactInformation) {
        this.name = name;
        this.contactInformation = contactInformation;
    }

    public User(String id, String name, ContactInformation contactInformation) {
        this.id = id;
        this.name = name;
        this.contactInformation = contactInformation;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
