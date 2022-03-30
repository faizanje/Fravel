package com.example.Utils;

import java.math.BigDecimal;

public class NumberUtils {

    public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static double getDiscountedPrice(double price){
        return (price - (price * 0.15));
    }


}
