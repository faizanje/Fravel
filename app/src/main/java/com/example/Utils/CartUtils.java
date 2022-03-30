package com.example.Utils;

import com.example.Models.CartItem;
import com.example.Models.Product;

public class CartUtils {

    public static void addToCart(Product product, String size, int quantity) {
        CartItem cartItem = new CartItem(product, size, quantity);
        FirebaseUtils.getCartReference()
                .child(product.getId())
                .setValue(cartItem);
    }

    public static void increment(CartItem cartItem) {
        addToCart(cartItem.getProduct(), cartItem.getVariant(), cartItem.getQuantity() + 1);
    }


    public static void decrement(CartItem cartItem) {
        addToCart(cartItem.getProduct(), cartItem.getVariant(), cartItem.getQuantity() - 1);
    }


    public static void removeItem(CartItem cartItem) {
        FirebaseUtils.getCartReference()
                .child(cartItem.getProduct().getId())
                .removeValue();
    }
}
