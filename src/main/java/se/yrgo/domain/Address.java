package se.yrgo.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    String street;
    String city;
    String zipcode;

    public Address() {  }
    public Address(String street, String city, String zipcode) {
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return street + " " + city + " " + zipcode;
    }
}
