package com.ntnu.wip.nabl.Models;

public class Company {
    private String name;
    private String orgnr;

    public Company(String name, String orgnr) {
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
}
