package com.example.fravell.Utils;

import com.example.fravell.Models.CartItem;

import java.math.BigDecimal;
import java.util.ArrayList;

public class NumberUtils {

    public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static double getDiscountedPrice(double price){
        return (price - (price * 0.15));
    }

    public static double getTotalAmountAndQuantity(ArrayList<CartItem> cartItems){
        int quantity = 0;
        double totalAmount = 7;
        for (CartItem cartItem : cartItems) {
            quantity += cartItem.getQuantity();
            totalAmount += NumberUtils.getDiscountedPrice(cartItem.getProduct().getPrice()) * cartItem.getQuantity();
        }
        return totalAmount;
    }
}
