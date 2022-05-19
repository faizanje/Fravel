package com.example.fravell.Models;

import java.io.Serializable;

public class FravellerCriteria implements Serializable {
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

    public FravellerCriteria setId(String id) {
        this.id = id;
        return this;
    }

    public String getToCity() {
        return toCity;
    }

    public FravellerCriteria setToCity(String toCity) {
        this.toCity = toCity;
        return this;
    }

    public String getFromCity() {
        return fromCity;
    }

    public FravellerCriteria setFromCity(String fromCity) {
        this.fromCity = fromCity;
        return this;
    }

    public long getStartDate() {
        return startDate;
    }

    public FravellerCriteria setStartDate(long startDate) {
        this.startDate = startDate;
        return this;
    }

    public long getEndDate() {
        return endDate;
    }

    public FravellerCriteria setEndDate(long endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getDescribeTheItem() {
        return describeTheItem;
    }

    public FravellerCriteria setDescribeTheItem(String describeTheItem) {
        this.describeTheItem = describeTheItem;
        return this;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public FravellerCriteria setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public FravellerCriteria setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public FravellerCriteria() {
    }

    public FravellerCriteria(String id, String toCity, String fromCity, long startDate, long endDate, String describeTheItem, String additionalNotes, String userId) {
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
