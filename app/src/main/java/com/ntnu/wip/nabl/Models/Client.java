package com.ntnu.wip.nabl.Models;


import com.ntnu.wip.nabl.Consts.Id;
import com.ntnu.wip.nabl.Utils;

/**
 * Client model is representing a client which you log hours against
 * Created by markusja on 4/11/18.
 */
public class Client {
    private String id;
    private String companyId;
    private String name;
    private ContactInformation contactInformation;
    private Address address;

    /**
     * Constructor for an empty client instance, with auto generated id
     */
    public Client(){
        id = Utils.generateUniqueId(Id.idLength);
        this.contactInformation = new ContactInformation();
        this.address = new Address();
    }

    /**
     * Full constructor without the need for setters, with auto generated id
     * @param name String - full name
     * @param contactInformation {@link ContactInformation}
     * @param address {@link Address}
     */
    public Client(String name, ContactInformation contactInformation, Address address) {
        id = Utils.generateUniqueId(Id.idLength);
        this.name = name;
        this.contactInformation = contactInformation;
        this.address = address;
    }

    /**
     * Getter for id
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
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
     * Getter for address
     * @return {@link Address}
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Setter for address
     * @param address {@link Address}
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Getter for Contact information
     * @return {@link ContactInformation}
     */
    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    /**
     * Setter for Contact information
     * @param contactInformation {@link ContactInformation}
     */
    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    /**
     * Setter for company Id
     * @param companyId String
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * Getter for company id
     * @return String
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * "toString()" representation of the object
     * @return String
     */
    @Override
    public String toString(){
        return this.name;
    }
}
