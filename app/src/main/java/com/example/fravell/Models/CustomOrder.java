package com.example.fravell.Models;

public class CustomOrder {
    String orderId;
    double totalAmount;
    long timeInMillis;
    String orderStatus;
    FravellerCriteria fravellerCriteria;
    AvailableFraveller availableFraveller;

    public CustomOrder(String orderId, double totalAmount, long timeInMillis, String orderStatus, FravellerCriteria fravellerCriteria, AvailableFraveller availableFraveller) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.timeInMillis = timeInMillis;
        this.orderStatus = orderStatus;
        this.fravellerCriteria = fravellerCriteria;
        this.availableFraveller = availableFraveller;
    }

    public CustomOrder() {
    }

    public String getOrderId() {
        return orderId;
    }

    public CustomOrder setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public CustomOrder setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public CustomOrder setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
        return this;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public CustomOrder setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public FravellerCriteria getFravellerCriteria() {
        return fravellerCriteria;
    }

    public CustomOrder setFravellerCriteria(FravellerCriteria fravellerCriteria) {
        this.fravellerCriteria = fravellerCriteria;
        return this;
    }

    public AvailableFraveller getAvailableFraveller() {
        return availableFraveller;
    }

    public CustomOrder setAvailableFraveller(AvailableFraveller availableFraveller) {
        this.availableFraveller = availableFraveller;
        return this;
    }
}
