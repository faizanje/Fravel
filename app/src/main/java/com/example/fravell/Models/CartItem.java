package com.example.fravell.Models;

import java.io.Serializable;

public class CartItem implements Serializable {
    Product product;
    String variant;
    int quantity;
    boolean feedbackLeft;

    public boolean isFeedbackLeft() {
        return feedbackLeft;
    }

    public void setFeedbackLeft(boolean feedbackLeft) {
        this.feedbackLeft = feedbackLeft;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartItem() {
        feedbackLeft = false;
    }

    public CartItem(Product product, String size, int quantity) {
        this.product = product;
        this.variant = size;
        this.quantity = quantity;
        feedbackLeft = false;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", variant='" + variant + '\'' +
                ", quantity=" + quantity +
                ", feedbackLeft=" + feedbackLeft +
                '}';
    }
}
