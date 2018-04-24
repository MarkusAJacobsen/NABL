package com.ntnu.wip.nabl.Models;

/**
 * Created by markusja on 4/11/18.
 */

public class Address {
    private String street;
    private int number;
    private int zipcode;
    private String city;

    public Address(){
        //NO.OP
    }

    public Address(String street, int number, int zipcode, String city) {
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
