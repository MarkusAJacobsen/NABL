package com.ntnu.wip.nabl.DataModels;

import java.util.Locale;

/**
 * Created by markusja on 4/11/18.
 */

public class Project {
    private int id;             //Internal id
    private int projectId;      //Human defined project id, still has to be unique
    private Address address;

    public Project(int id, int projectId, Address address) {
        this.id = id;
        this.projectId = projectId;
        this.address = address;
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
}
