package de.xitaso.taskman.entities;

public class Address {
    private String city;
    private String street;
    private String streetNumber;
    private String zipCode;

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
