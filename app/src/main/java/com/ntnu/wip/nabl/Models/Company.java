package com.ntnu.wip.nabl.Models;

import com.ntnu.wip.nabl.Utils;

public class Company {
    private String id;
    private String name;
    private String orgnr;
    private String ownerId;

    public Company() {
        //NO-OP
    }

    public Company(String name, String orgnr) {
        this.name = name;
        this.orgnr = orgnr;
        Utils.generateUniqueId(24);
    }

    public Company(String id, String name, String orgnr) {
        this.id = id;
        this.name = name;
        this.orgnr = orgnr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgnr() {
        return orgnr;
    }

    public void setOrgnr(String orgnr) {
        this.orgnr = orgnr;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }
}
