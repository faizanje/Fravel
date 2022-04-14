package com.example.fravell.Models;

public class AvailableFraveller {
    String id;
    String name;
    String fromCity;
    String toCity;
    long fromDateInMillis;
    long toDateInMillis;

    public AvailableFraveller() {
    }

    public String getId() {
        return id;
    }

    public AvailableFraveller setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AvailableFraveller setName(String name) {
        this.name = name;
        return this;
    }

    public String getFromCity() {
        return fromCity;
    }

    public AvailableFraveller setFromCity(String fromCity) {
        this.fromCity = fromCity;
        return this;
    }

    public String getToCity() {
        return toCity;
    }

    public AvailableFraveller setToCity(String toCity) {
        this.toCity = toCity;
        return this;
    }

    public long getFromDateInMillis() {
        return fromDateInMillis;
    }

    public AvailableFraveller setFromDateInMillis(long fromDateInMillis) {
        this.fromDateInMillis = fromDateInMillis;
        return this;
    }

    public long getToDateInMillis() {
        return toDateInMillis;
    }

    public AvailableFraveller setToDateInMillis(long toDateInMillis) {
        this.toDateInMillis = toDateInMillis;
        return this;
    }

    public AvailableFraveller(String id, String name, String fromCity, String toCity, long fromDateInMillis, long toDateInMillis) {
        this.id = id;
        this.name = name;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.fromDateInMillis = fromDateInMillis;
        this.toDateInMillis = toDateInMillis;
    }
}
