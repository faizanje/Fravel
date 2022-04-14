package com.example.fravell.Models;

public enum OrderStatus {
    PROCESSING("Processing"),
    DELIVERED("Delivered");

    final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
