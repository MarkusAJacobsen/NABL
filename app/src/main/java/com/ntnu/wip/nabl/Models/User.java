package com.ntnu.wip.nabl.Models;

/**
 * System user model
 */
public class User {
    private String id;
    private String name;
    private ContactInformation contactInformation;


    /**
     * Constructor without id
     * @param name String
     * @param contactInformation {@link ContactInformation}
     */
    public User(String name, ContactInformation contactInformation) {
        this.name = name;
        this.contactInformation = contactInformation;
    }

    /**
     * Full constructor
     * @param id String
     * @param name String
     * @param contactInformation {@link ContactInformation}
     */
    public User(String id, String name, ContactInformation contactInformation) {
        this.id = id;
        this.name = name;
        this.contactInformation = contactInformation;
    }

    /**
     * Getter for contact information
     * @return {@link ContactInformation}
     */
    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    /**
     * Getter for name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for id
     * @return String
     */
    public String getId() {
        return id;
    }
}
