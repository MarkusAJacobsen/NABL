package com.ntnu.wip.nabl.Models;

import com.ntnu.wip.nabl.Consts.Id;
import com.ntnu.wip.nabl.Utils;

/**
 * Model of company
 */
public class Company {
    private String id;
    private String name;
    private String orgnr;
    private String ownerId;

    /**
     * Empty constructor
     */
    public Company() {}

    /**
     * Constructor with auto generated id
     * @param name String
     * @param orgnr String
     */
    public Company(String name, String orgnr) {
        this.name = name;
        this.orgnr = orgnr;
        this.id = Utils.generateUniqueId(Id.idLength);
    }

    /**
     * Full constructor
     * @param id String
     * @param name String
     * @param orgnr String
     */
    public Company(String id, String name, String orgnr) {
        this.id = id;
        this.name = name;
        this.orgnr = orgnr;
    }

    /**
     * Getter for name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for organisation number
     * @return String
     */
    public String getOrgnr() {
        return orgnr;
    }

    /**
     * Setter for organisation number
     * @param orgnr String
     */
    public void setOrgnr(String orgnr) {
        this.orgnr = orgnr;
    }

    /**
     * Setter for id
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter for Owner id
     * @param ownerId String
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Getter for id
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for owner id
     * @return String
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * "toString()" representation of the object
     * @return String
     */
    @Override
    public String toString() {
        return this.name + "- " + this.orgnr;
    }
}
