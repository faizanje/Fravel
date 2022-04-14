package com.example.fravell.Models;

import java.io.Serializable;

public class CustomOrder implements Serializable {
    String id;
    String toCity;
    String fromCity;
    long startDate;
    long endDate;
    String describeTheItem;
    String additionalNotes;
    String userId;

    public String getId() {
        return id;
    }

    public CustomOrder setId(String id) {
        this.id = id;
        return this;
    }

    public String getToCity() {
        return toCity;
    }

    public CustomOrder setToCity(String toCity) {
        this.toCity = toCity;
        return this;
    }

    public String getFromCity() {
        return fromCity;
    }

    public CustomOrder setFromCity(String fromCity) {
        this.fromCity = fromCity;
        return this;
    }

    public long getStartDate() {
        return startDate;
    }

    public CustomOrder setStartDate(long startDate) {
        this.startDate = startDate;
        return this;
    }

    public long getEndDate() {
        return endDate;
    }

    public CustomOrder setEndDate(long endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getDescribeTheItem() {
        return describeTheItem;
    }

    public CustomOrder setDescribeTheItem(String describeTheItem) {
        this.describeTheItem = describeTheItem;
        return this;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public CustomOrder setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public CustomOrder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public CustomOrder() {
    }

    public CustomOrder(String id, String toCity, String fromCity, long startDate, long endDate, String describeTheItem, String additionalNotes, String userId) {
        this.id = id;
        this.toCity = toCity;
        this.fromCity = fromCity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.describeTheItem = describeTheItem;
        this.additionalNotes = additionalNotes;
        this.userId = userId;
    }
}
