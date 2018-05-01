package com.ntnu.wip.nabl.Models;

/**
 * Model representing an address
 * Created by markusja on 4/11/18.
 */
public class Address {
    private String street;
    private int number;
    private int zipcode;
    private String city;

    /**
     * Used to create an empty Address instance
     */
    public Address(){}

    /**
     * Full constructor, without the need for setters
     * @param street String
     * @param number int - Street number
     * @param zipcode - int
     * @param city - String
     */
    public Address(String street, int number, int zipcode, String city) {
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.city = city;
    }

    /**
     * Getter for street
     * @return String
     */
    public String getStreet() {
        return street;
    }

    /**
     * Setter for street
     * @param street String
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Getter for number - where the number represents the street number
     * @return int
     */
    public int getNumber() {
        return number;
    }

    /**
     * Setter for number - where the number represents the street number
     * @param number int
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Getter for zipcode
     * @return int
     */
    public int getZipcode() {
        return zipcode;
    }

    /**
     * Setter for zipcode
     * @param zipcode int
     */
    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * Getter for city
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter for city
     * @param city String
     */
    public void setCity(String city) {
        this.city = city;
    }
}
