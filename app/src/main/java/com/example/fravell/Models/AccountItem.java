package com.example.fravell.Models;

public class AccountItem {
    String title;
    int imageId;

    public AccountItem(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public AccountItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getImageId() {
        return imageId;
    }

    public AccountItem setImageId(int imageId) {
        this.imageId = imageId;
        return this;
    }
}
