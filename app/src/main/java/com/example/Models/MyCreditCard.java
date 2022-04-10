package com.example.Models;

import java.io.Serializable;

public class MyCreditCard implements Serializable {
    String cardNumber;
    int expiryMonth;
    int expiryYear;
    String cvc;
    String cardBrand;
    public MyCreditCard() {
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public MyCreditCard setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public MyCreditCard setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public MyCreditCard setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    public String getCvc() {
        return cvc;
    }

    public MyCreditCard setCvc(String cvc) {
        this.cvc = cvc;
        return this;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public MyCreditCard setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
        return this;
    }

    public MyCreditCard(String cardNumber, int expiryMonth, int expiryYear, String cvc, String cardBrand) {
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvc = cvc;
        this.cardBrand = cardBrand;
    }
}
