package com.example.fravell.Models;

import java.io.Serializable;

public class Review implements Serializable {
    String orderId;
    String userId;
    String userName;
    String review;
    String imageUrl;
    float rating;
    long timeInMillis;
    String reviewId;

    public String getUserName() {
        return userName;
    }

    public Review setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public Review setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
        return this;
    }

    public String getReviewId() {
        return reviewId;
    }

    public Review setReviewId(String reviewId) {
        this.reviewId = reviewId;
        return this;
    }

    public Review() {
    }

    public Review(String orderId, String userId, String review, String imageUrl, float rating, long timeInMillis) {
        this.orderId = orderId;
        this.userId = userId;
        this.review = review;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.timeInMillis = timeInMillis;
    }

    public String getOrderId() {
        return orderId;
    }

    public Review setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Review setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getReview() {
        return review;
    }

    public Review setReview(String review) {
        this.review = review;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Review setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public float getRating() {
        return rating;
    }

    public Review setRating(float rating) {
        this.rating = rating;
        return this;
    }
}
