package com.example.fravell.Models;

import java.io.Serializable;

public class Product implements Serializable {
    String id;
    String title;
    float price;
    String manufacturer;
    String description;
    String category;
    String image;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Product(String id, String title, float price, String manufacturer, String description, String category, String image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.manufacturer = manufacturer;
        this.description = description;
        this.category = category;
        this.image = image;
    }

    public Product() {
    }
}
