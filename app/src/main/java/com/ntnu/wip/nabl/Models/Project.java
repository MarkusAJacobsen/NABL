package com.ntnu.wip.nabl.Models;

import com.ntnu.wip.nabl.Consts.Id;
import com.ntnu.wip.nabl.Utils;

import java.util.Date;
import java.util.Locale;

/**
 * Project represents a more formal assignment than that of a {@link Client}
 * Which means that it has more information
 * Created by markusja on 4/11/18.
 */
public class Project {
    private String id;             //Internal id
    private int projectId;      //Human defined project id, still has to be unique
    private String name;
    private String description;
    private State state;
    private Category category;
    private Date start;
    private Date end;
    private Company company;
    private Address address;

    /**
     * Empty constructor, with auto generated id
     */
    public Project(){
        id = Utils.generateUniqueId(Id.idLength);
        state = State.PLANNING;
        category = Category.NULL;
        company = new Company();
        address = new Address();
    }

    /**
     * Full constructor, with auto generated id
     * TODO Should really be refactored to have an internal Builder class
     * @param projectId  int - external id
     * @param address {@link Address}
     * @param name String - textual representation of the project
     * @param description String
     * @param state {@link State}
     * @param category {@link Category}
     * @param start Date
     * @param end Date
     * @param company {@link Company}
     */
    public Project(int projectId, Address address, String name, String description, State state,
                   Category category, Date start, Date end, Company company) {
        id = Utils.generateUniqueId(Id.idLength);
        this.projectId = projectId;
        this.address = address;
        this.name = name;
        this.description = description;
        this.state = state;
        this.category = category;
        this.start = start;
        this.end = end;
        this.company = company;
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
     * Getter for project id
     * @return int
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Setter for project id
     * @param projectId int
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
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
     * Getter for description
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for state
     * @return {@link State}
     */
    public State getState() {
        return state;
    }

    /**
     * Setter for state
     * @param state {@link State}
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Getter for category
     * @return {@link Category}
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Setter for category
     * @param category {@link Category}
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Getter for project start date
     * @return Date
     */
    public Date getStart() {
        return start;
    }

    /**
     * Setter for project start date
     * @param start Date
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Getter for project end date
     * @return Date
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Setter for project end date
     * @param end Date
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * Getter for company
     * @return {@link Company}
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Setter for company
     * @param company {@link Company}
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * "toString()" representation of this object
     * @return String
     */
    @Override
    public String toString(){
        return String.format(Locale.getDefault(), "%d %s %d",
                this.projectId,
                this.address.getStreet(),
                this.address.getNumber());
    }
}
