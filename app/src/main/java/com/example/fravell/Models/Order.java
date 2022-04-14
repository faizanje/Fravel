package com.example.fravell.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    String orderId;
    double totalAmount;
    long timeInMillis;
    String orderStatus;
    ArrayList<CartItem> cartItemArrayList = new ArrayList<>();
    UserAddress userAddress;
//    boolean feedbackLeft = false;
//
//    public boolean hasFeedbackLeft() {
//        return feedbackLeft;
//    }
//
//    public Order setFeedbackLeft(boolean feedbackLeft) {
//        this.feedbackLeft = feedbackLeft;
//        return this;
//    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public Order setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
        return this;
    }

    public Order(String orderId, double totalAmount, long timeInMillis, String orderStatus, ArrayList<CartItem> cartItemArrayList, UserAddress userAddress) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.timeInMillis = timeInMillis;
        this.orderStatus = orderStatus;
        this.cartItemArrayList = cartItemArrayList;
        this.userAddress = userAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Order setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public Order setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public Order(String orderId, long timeInMillis, String orderStatus, ArrayList<CartItem> cartItemArrayList) {
        this.orderId = orderId;
        this.timeInMillis = timeInMillis;
        this.orderStatus = orderStatus;
        this.cartItemArrayList = cartItemArrayList;
    }

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public Order setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public Order setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
        return this;
    }

    public ArrayList<CartItem> getCartItemArrayList() {
        return cartItemArrayList;
    }

    public Order setCartItemArrayList(ArrayList<CartItem> cartItemArrayList) {
        this.cartItemArrayList = cartItemArrayList;
        return this;
    }

    public Order(String orderId, long timeInMillis, ArrayList<CartItem> cartItemArrayList) {
        this.orderId = orderId;
        this.timeInMillis = timeInMillis;
        this.cartItemArrayList = cartItemArrayList;
    }
}
