package com.example.fravell.Models;

import java.io.Serializable;

public class UserAddress implements Serializable {
    String name;
    String address;
    String city;
    String zipCode;
    String province;
    String country;

    public UserAddress() {
    }

    public String getName() {
        return name;
    }

    public UserAddress setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserAddress setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserAddress setCity(String city) {
        this.city = city;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public UserAddress setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public UserAddress setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserAddress setCountry(String country) {
        this.country = country;
        return this;
    }

    public UserAddress(String name, String address, String city, String zipCode, String province, String country) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.province = province;
        this.country = country;
    }
}
