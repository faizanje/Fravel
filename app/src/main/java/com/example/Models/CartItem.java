package com.example.Models;

import java.io.Serializable;

public class CartItem implements Serializable {
    Product product;
    String variant;
    int quantity;

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
    }

    public CartItem(Product product, String size, int quantity) {
        this.product = product;
        this.variant = size;
        this.quantity = quantity;
    }
}
