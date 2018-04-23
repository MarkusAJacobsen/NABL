package com.ntnu.wip.nabl.Models;

import java.util.Date;
import java.util.Locale;

/**
 * Created by markusja on 4/11/18.
 */

public class Project {
    private int id;             //Internal id
    private int projectId;      //Human defined project id, still has to be unique
    private String name;
    private String description;
    private State state;
    private Category category;
    private Date start;
    private Date end;
    private Company company;
    private Address address;

    public Project(){
        state = State.PLANNING;
        category = Category.NULL;
        company = new Company();
        address = new Address();
    }

    public Project(int id, int projectId, Address address, String name, String description, State state,
                   Category category, Date start, Date end, Company company) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString(){
        return String.format(Locale.getDefault(), "%d %s %d",
                this.projectId,
                this.address.getStreet(),
                this.address.getNumber());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
